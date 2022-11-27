package com.fmi.homeflow.service;

import com.fmi.homeflow.model.User;
import com.fmi.homeflow.model.dto.UserDetailsDto;
import com.fmi.homeflow.model.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@AllArgsConstructor
public final class UserFacade {

    private final UserService userService;

    public UserDetailsDto getUserByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return UserDetailsDto.builder()
                .name(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public URI addUser(UserDto user) {
        User newUser = userService.addUser(user);
        return userService.createUserURI(newUser);
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        return userService.updateUser(username, userDetailsDto);
    }

    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

}
