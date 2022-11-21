package com.fmi.homeflow.service;

import com.fmi.homeflow.exception.InvalidDataException;
import com.fmi.homeflow.exception.user_exception.TaskAlreadyExistsException;
import com.fmi.homeflow.exception.user_exception.TaskNotFoundException;
import com.fmi.homeflow.model.Task;
import com.fmi.homeflow.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    public Task getTaskById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public void deleteTaskById(UUID id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.deleteById(id);
    }

    public void addTask(Task task) {
        Optional<Task> optionalTask = taskRepository.findById(task.getTaskId());
        if (optionalTask.isPresent()) {
            throw new TaskAlreadyExistsException(task.getTaskId());
        }

        //TODO:Explain what validate has to check @Bogdan
        //validateTask(task);

        Task savedTask = taskRepository.save(task);

        if (savedTask.getAssignee() != null) {
            notificationService.notifyUser(task.getAssignee().getId(), task);
        }
    }

    /*public boolean validateTask(Task task) {
        Family family = familyService.getFamilyById(task.getFamilyId());
        if (task.getAssignee() != null) {
            User user = userService.getUserById(task.getAssignee());
            boolean isInFamily = familyService.memberIsInFamily(user.getId(), family.getId());
            if (!isInFamily) {
                throw new InvalidDataException();
            }
        }
        return true;
    }*/

    /*public void notifyIfNeeded(Task previousTask, Task currentTask) {
        if (!previousTask.getAssignee().equals(currentTask.getAssignee())) {
            notificationService.notifyUser(currentTask.getAssignee(), currentTask);
        }
    }

    public void updateTask(Task task) {
        Task previousTask = getTaskById(task.getTaskId());
        if (validateTask(task)) {
            database.put(task.getTaskId(), task);
            notifyIfNeeded(previousTask, task);
        }
        throw new TaskNotFoundException(task.getTaskId());
    }*/

    /*public void patchTask(Task task) {
        Task existingTask = getTaskById(task.getTaskId());
        boolean changedAssignee = false;
        if (task.getName() != null) {
            existingTask.setName(task.getName());
        }
        if (task.getAssignee() != null) {
            changedAssignee = task.getAssignee().equals(existingTask.getAssignee());
            existingTask.setAssignee(task.getAssignee());
        }
        if (task.getFamilyId() != null) {
            existingTask.setFamilyId(task.getFamilyId());
        }
        if (task.getState() != null) {
            existingTask.setState(task.getState());
        }
        if (validateTask(existingTask)) {
            database.put(task.getTaskId(), existingTask);
            if (changedAssignee && existingTask.getAssignee() != null) {
                notificationService.notifyUser(existingTask.getAssignee(), existingTask);
            }
        }
    }*/
}