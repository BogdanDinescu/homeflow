package com.fmi.homeflow.service.notifications;

import com.fmi.homeflow.model.task.TaskEntity;

import java.util.Optional;

public interface Observer {
    void update(Notification notification);

    default Optional<String> textFromTask(TaskEntity taskEntity) {
        if (taskEntity == null) {
            return Optional.empty();
        }
        if (taskEntity.getName() == null) {
            return Optional.empty();
        }
        if (taskEntity.getState() == null) {
            return Optional.empty();
        }
        StringBuffer text = new StringBuffer();
        text.append("Task ");
        text.append(taskEntity.getName());
        text.append(" was appointed to you\n");
        text.append("State: ");
        text.append(taskEntity.getState().toString());
        text.append("\n HomeFlow");
        return Optional.of(text.toString());
    }
}
