package com.fmi.homeflow.service;

import com.fmi.homeflow.exception.user_exception.UserAlreadyExistsException;
import com.fmi.homeflow.exception.user_exception.UserNotFoundException;
import com.fmi.homeflow.model.Role;
import com.fmi.homeflow.model.User;
import com.fmi.homeflow.model.dto.UserDetailsDto;
import com.fmi.homeflow.model.dto.UserDto;
import com.fmi.homeflow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String addUser(UserDto userDto) {
        return userRepository.save(User.builder()
                        .username(userDto.getName())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .role(Role.MEMBER)
                        .build()).getUsername();
    }

    public UserDetailsDto getUserDtoByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> UserDetailsDto.builder()
                        .name(user.getUsername())
                        .build())
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        return userRepository.findByUsername(username)
                .map(optionalUser -> {
                    optionalUser.setUsername(userDetailsDto.getName());
                    return optionalUser;
                })
                .map(userRepository::save)
                .map(savedUser -> UserDetailsDto.builder()
                        .name(savedUser.getUsername())
                        .build()
                )
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User upsertUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        userRepository.deleteByUsername(username);
    }

}
