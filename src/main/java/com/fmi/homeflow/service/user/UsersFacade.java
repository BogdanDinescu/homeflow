package com.fmi.homeflow.service.user;

import com.fmi.homeflow.model.dto.user.CreateUserRequest;
import com.fmi.homeflow.model.dto.user.GetUserResponse;
import com.fmi.homeflow.model.dto.user.UserDetailsDto;
import com.fmi.homeflow.model.user.UserEntity;
import com.fmi.homeflow.transformer.UsersMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@AllArgsConstructor
public class UsersFacade {

    private final UsersService usersService;

    private final UsersMapper usersMapper;

    private final PasswordEncoder passwordEncoder;

    public GetUserResponse getUserByUsername(String username) {
        return usersMapper.mapToGetUserResponse(usersService.getUserByUsername(username));
    }

    public URI addUser(CreateUserRequest createUserRequest) {
        UserEntity userToSave = usersMapper.mapToUserEntity(createUserRequest, passwordEncoder);
        usersService.checkIfUsernameIsAlreadyUsed(userToSave);
        UserEntity savedUser = usersService.addUser(userToSave);
        return usersService.createUserURI(savedUser);
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        return usersMapper.mapToUserDetailsDto(usersService.updateUser(username, userDetailsDto));
    }

    public void deleteUser(String username) {
        usersService.deleteUser(username);
    }

}
