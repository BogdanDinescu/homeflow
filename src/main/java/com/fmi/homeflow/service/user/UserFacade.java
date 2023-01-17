package com.fmi.homeflow.service.user;

import com.fmi.homeflow.exception.user_exception.UserAlreadyExistsException;
import com.fmi.homeflow.model.UserEntity;
import com.fmi.homeflow.model.dto.UserDetailsDto;
import com.fmi.homeflow.model.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@AllArgsConstructor
public class UserFacade {

    private final UserService userService;

    public UserDetailsDto getUserByUsername(String username) {
        UserEntity userEntity = userService.getUserByUsername(username);
        return UserDetailsDto.builder()
            .id(userEntity.getId())
            .name(userEntity.getUsername())
            .email(userEntity.getEmail())
            .familyId(userEntity.getUserFamily() != null ? userEntity.getUserFamily().getId() : null)
            .build();
    }

    public URI addUser(UserDto user) {
        UserEntity newUserEntity = userService.addUser(user);
        return userService.createUserURI(newUserEntity);
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        UserEntity existingUserEntity = userService.getUserByUsername(username);

        if (username.equals(existingUserEntity.getUsername())) {
            throw new UserAlreadyExistsException(username);
        }

        return userService.updateUser(existingUserEntity, userDetailsDto);
    }

    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

}
