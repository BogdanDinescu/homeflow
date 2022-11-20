package com.fmi.homeflow.service;

import com.fmi.homeflow.exception.user_exception.UserAlreadyExistsException;
import com.fmi.homeflow.exception.user_exception.UserNotFoundException;
import com.fmi.homeflow.model.User;
import com.fmi.homeflow.model.dto.UserDto;
import com.fmi.homeflow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User addUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    /*public UserDto getUserDtoById(UUID id) {
        User existingUser = database.get(id);

        if (existingUser != null) {
            return new UserDto(existingUser.getId(), existingUser.getName());
        }

        throw new UserNotFoundException(id);
    }

    public User getUserById(UUID id) {
        User existingUser = database.get(id);
        if (existingUser == null) {
            throw new UserNotFoundException(id);
        }
        return existingUser;
    }

    public void updateUser(User user) throws UserNotFoundException {
        if (userExists(user)) {
            database.put(user.getId(), user);
        } else {
            throw new UserNotFoundException(user.getId());
        }
    }

    public boolean userExistsById(UUID id) {
        return database.containsKey(id);
    }

    public boolean userExists(User user) {
        return userExistsById(user.getId());
    }

    public void deleteUserById(UUID id) {
        database.remove(id);
    }

    public void deleteUser(User user) {
        deleteUserById(user.getId());
    }*/

    public Optional<User> findByName(String name) {
        return userRepository.findAll().stream().filter(user -> user.getName().equals(name)).findFirst();
    }
}
