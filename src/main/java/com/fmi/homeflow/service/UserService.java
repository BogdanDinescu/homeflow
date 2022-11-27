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

import java.net.URI;

import static com.fmi.homeflow.utility.UserConstants.GET_USER_ROUTE;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User addUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getName())) {
            throw new UserAlreadyExistsException(userDto.getName());
        }

        return userRepository.save(User.builder()
                        .username(userDto.getName())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .role(Role.MEMBER)
                        .build());
    }

    public URI createUserURI(User user) {
        return URI.create(GET_USER_ROUTE + user.getUsername());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        return userRepository.findByUsername(username)
                .map(optionalUser -> {
                    optionalUser.setUsername(userDetailsDto.getName());
                    optionalUser.setFirstName(userDetailsDto.getFirstName());
                    optionalUser.setLastName(userDetailsDto.getLastName());
                    optionalUser.setEmail(userDetailsDto.getEmail());
                    optionalUser.setPhone(userDetailsDto.getPhone());
                    return optionalUser;
                })
                .map(userRepository::save)
                .map(savedUser -> UserDetailsDto.builder()
                        .name(savedUser.getUsername())
                        .firstName(savedUser.getFirstName())
                        .lastName(savedUser.getLastName())
                        .build()
                )
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        userRepository.deleteByUsername(username);
    }

}
