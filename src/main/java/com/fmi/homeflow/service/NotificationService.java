package com.fmi.homeflow.service;

import com.fmi.homeflow.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService {

    public void notifyUser(UUID userId, Task task) {
        // TODO THIS IS A STUB METHOD
        return;
    }
}
