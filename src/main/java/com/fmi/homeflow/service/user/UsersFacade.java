package com.fmi.homeflow.service.user;

import com.fmi.homeflow.model.dto.user.CreateUserRequest;
import com.fmi.homeflow.model.dto.user.UserDetailsDto;
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

    public UserDetailsDto getUserByUsername(String username) {
        return usersMapper.mapToUserDetailsDto(usersService.getUserByUsername(username));
    }

    public URI addUser(CreateUserRequest createUserRequest) {
        return usersService.createUser(usersMapper.mapToUserEntity(createUserRequest, passwordEncoder));
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        return usersMapper.mapToUserDetailsDto(usersService.updateUser(username, userDetailsDto));
    }

    public void deleteUser(String username) {
        usersService.deleteUser(username);
    }

}
