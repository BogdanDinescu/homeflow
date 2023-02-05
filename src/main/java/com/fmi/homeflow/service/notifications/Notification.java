package com.fmi.homeflow.service.notifications;

import com.fmi.homeflow.model.task.TaskEntity;
import com.fmi.homeflow.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Notification {
    private UserEntity userEntity;
    private TaskEntity taskEntity;
}
