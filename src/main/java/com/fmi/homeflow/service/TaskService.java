package com.fmi.homeflow.service;

import com.fmi.homeflow.exception.InvalidDataException;
import com.fmi.homeflow.exception.user_exception.TaskAlreadyExistsException;
import com.fmi.homeflow.exception.user_exception.TaskNotFoundException;
import com.fmi.homeflow.model.Family;
import com.fmi.homeflow.model.Task;
import com.fmi.homeflow.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private final Map<UUID, Task> database;
    private final FamilyService familyService;
    private final UserService userService;
    private final NotificationService notificationService;

    private synchronized UUID generateUUID() {
        return UUID.randomUUID();
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
    }

    public void notifyIfNeeded(Task previousTask, Task currentTask) {
        if (!previousTask.getAssignee().equals(currentTask.getAssignee())) {
            notificationService.notifyUser(currentTask.getAssignee(), currentTask);
        }
    }*/

    /*public void addTask(Task task) {
        if (task.getTaskId() == null) {
            task.setTaskId(generateUUID());
        }
        if (database.containsKey(task.getTaskId())) {
            throw new TaskAlreadyExistsException(task.getTaskId());
        }
        validateTask(task);
        database.put(task.getTaskId(), task);
        if (task.getAssignee() != null) {
            notificationService.notifyUser(task.getAssignee(), task);
        }
    }*/

    public Task getTaskById(UUID id) {
        Task task = database.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    public boolean taskExistsById(UUID id) {
        return database.containsKey(id);
    }

    public boolean taskExists(Task task) {
        return taskExistsById(task.getTaskId());
    }

    /*public void updateTask(Task task) {
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

    public boolean deleteTaskById(UUID id) {
        return database.remove(id) != null;
    }

    public boolean deleteTask(Task task) {
        return deleteTaskById(task.getTaskId());
    }
}