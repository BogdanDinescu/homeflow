package com.fmi.homeflow.service;

import com.fmi.homeflow.model.User;
import com.fmi.homeflow.model.dto.UserDetailsDto;
import com.fmi.homeflow.model.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public String addUser(UserDto user) {
        return userService.addUser(user);
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        return userService.updateUser(username, userDetailsDto);
    }

    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

}
