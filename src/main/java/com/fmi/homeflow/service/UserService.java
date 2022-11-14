package com.fmi.homeflow.service;

import com.fmi.homeflow.exception.UserAlreadyExistsException;
import com.fmi.homeflow.exception.UserNotFoundException;
import com.fmi.homeflow.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    public Map<String, User> database;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService() {
        database = new HashMap<>();
    }

    public void addUser(User user) {
        if (userExists(user)) {
            throw new UserAlreadyExistsException(user.getId());
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            database.put(user.getId(), user);
        }
    }

    public Optional<User> getUserById(String id) {
        return Optional.of(database.get(id));
    }

    public void updateUser(User user) throws UserNotFoundException {
        if (userExists(user)) {
            database.put(user.getId(), user);
        } else {
            throw new UserNotFoundException(user.getId());
        }
    }

    public boolean userExistsById(String id) {
        return database.containsKey(id);
    }

    public boolean userExists(User user) {
        return userExistsById(user.getId());
    }

    public void deleteUserById(String id) {
        database.remove(id);
    }

    public void deleteUser(User user) {
        deleteUserById(user.getId());
    }

    public Optional<User> findByName(String name) {
        return database.values().stream().filter(user -> user.getName().equals(name)).findFirst();
    }
}
