package com.fmi.homeflow.service.task;

import com.fmi.homeflow.exception.InvalidDataException;
import com.fmi.homeflow.exception.user_exception.TaskNotFoundException;
import com.fmi.homeflow.model.family.FamilyEntity;
import com.fmi.homeflow.model.task.TaskEntity;
import com.fmi.homeflow.model.user.UserEntity;
import com.fmi.homeflow.repository.TaskRepository;
import com.fmi.homeflow.service.notifications.NotificationsService;
import com.fmi.homeflow.service.user.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationsService notificationsService;
    private final UsersService usersService;

    public TaskEntity getTaskById(UUID id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<TaskEntity> getTasksInFamily(FamilyEntity familyEntity) {
        return taskRepository.findTaskByFamilyEntity(familyEntity);
    }

    public void deleteTaskById(UUID id) {
        taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.deleteById(id);
    }

    public void addTask(TaskEntity taskEntity) {
        if (validateTask(taskEntity)) {
            TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
            if (savedTaskEntity.getAssignee() != null) {
                notificationsService.notifyUser(taskEntity.getAssignee(), taskEntity);
            }
        }
    }

    /**
     * Validates a task
     * <ul>
     *     <li>The task is present in a family (a task is created within a family)</li>
     *     <li>If the task is assigned to someone, verify that the that user is a member of that family</li>
     * </ul>
     *
     * @param taskEntity the task to validate
     * @return <ul><li>true if it's valid</li>
     *         <li>false if it's not</li></ul>
     * @throws InvalidDataException if the task has no family assigned.
     */
    public boolean validateTask(TaskEntity taskEntity) {
        FamilyEntity familyEntity = taskEntity.getFamilyEntity();
        if (familyEntity == null) {
            throw new InvalidDataException();
        }
        if (taskEntity.getAssignee() != null) {
            UserEntity userEntity = taskEntity.getAssignee();
            boolean isInFamily = usersService.memberIsInFamily(userEntity, familyEntity.getId());
            if (!isInFamily) {
                throw new InvalidDataException();
            }
        }
        return true;
    }

    /**
     * Calls the notification service if the task have the assignee changed
     *
     * @param previousTaskEntity previous task
     * @param currentTaskEntity  the task to be saved
     */
    public void notifyIfNeeded(TaskEntity previousTaskEntity, TaskEntity currentTaskEntity) {
        if (currentTaskEntity.getAssignee() != null && !currentTaskEntity.getAssignee().equals(previousTaskEntity.getAssignee())) {
            notificationsService.notifyUser(currentTaskEntity.getAssignee(), currentTaskEntity);
        }
    }

    public void updateTask(TaskEntity taskEntity) {
        TaskEntity previousTaskEntity = getTaskById(taskEntity.getId());
        if (validateTask(taskEntity)) {
            taskRepository.save(taskEntity);
            notifyIfNeeded(previousTaskEntity, taskEntity);
        }
    }

    public void patchTask(TaskEntity taskEntity) {
        TaskEntity existingTaskEntity = getTaskById(taskEntity.getId());
        boolean changedAssignee = false;
        if (taskEntity.getName() != null) {
            existingTaskEntity.setName(taskEntity.getName());
        }
        if (taskEntity.getState() != null) {
            existingTaskEntity.setState(taskEntity.getState());
        }
        if (taskEntity.getAssignee() != null) {
            changedAssignee = taskEntity.getAssignee().equals(existingTaskEntity.getAssignee());
            existingTaskEntity.setAssignee(taskEntity.getAssignee());
        }
        if (validateTask(existingTaskEntity)) {
            taskRepository.save(existingTaskEntity);
            if (changedAssignee && existingTaskEntity.getAssignee() != null) {
                notificationsService.notifyUser(existingTaskEntity.getAssignee(), existingTaskEntity);
            }
        }
    }
}