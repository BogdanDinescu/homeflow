package com.fmi.homeflow.service.user;

import com.fmi.homeflow.exception.user_exception.UserAlreadyExistsException;
import com.fmi.homeflow.exception.user_exception.UserNotFoundException;
import com.fmi.homeflow.model.Role;
import com.fmi.homeflow.model.UserEntity;
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

    public UserEntity addUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getName())) {
            throw new UserAlreadyExistsException(userDto.getName());
        }

        return userRepository.save(UserEntity.builder()
                .username(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.MEMBER)
                .build());
    }

    public URI createUserURI(UserEntity userEntity) {
        return URI.create(GET_USER_ROUTE + userEntity.getUsername());
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserDetailsDto updateUser(UserEntity userEntity, UserDetailsDto userDetailsDto) {
        UserEntity updatedUserEntity = updateUser(updateUserWithGivenDetails(userEntity, userDetailsDto));
        return UserDetailsDto.builder()
                .name(updatedUserEntity.getUsername())
                .firstName(updatedUserEntity.getFirstName())
                .lastName(updatedUserEntity.getLastName())
                .email(updatedUserEntity.getEmail())
                .phone(updatedUserEntity.getPhone())
                .familyId(updatedUserEntity.getUserFamily().getId())
                .build();
    }

    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        userRepository.deleteByUsername(username);
    }

    private UserEntity updateUserWithGivenDetails(UserEntity userEntity, UserDetailsDto userDetailsDto) {
        userEntity.setUsername(userDetailsDto.getName() == null ? userEntity.getUsername() : userDetailsDto.getName());
        userEntity.setFirstName(userDetailsDto.getFirstName() == null ? userEntity.getFirstName() : userDetailsDto.getFirstName());
        userEntity.setLastName(userDetailsDto.getLastName() == null ? userEntity.getLastName() : userDetailsDto.getLastName());
        userEntity.setEmail(userDetailsDto.getEmail() == null ? userEntity.getEmail() : userDetailsDto.getEmail());
        userEntity.setPhone(userDetailsDto.getPhone() == null ? userEntity.getPhone() : userDetailsDto.getPhone());
        return userEntity;
    }

}
