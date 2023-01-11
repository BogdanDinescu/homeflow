package com.fmi.homeflow.service.notifications;

import com.fmi.homeflow.model.Task;
import com.fmi.homeflow.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    List<Observer> observers;

    @PostConstruct
    public void init() {
        addObserver(new EmailNotifier());
        addObserver(new SMSNotifier());
    }

    public void notifyUser(UserEntity userEntity, Task task) {
        observers.forEach(observer -> observer.update(new Notification(userEntity, task)));
    }

    public synchronized void addObserver(Observer observer) {
        observers.add(observer);
    }

    public synchronized void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
