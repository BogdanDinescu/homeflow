package com.fmi.homeflow.service.user;

import com.fmi.homeflow.exception.user_exception.UserAlreadyExistsException;
import com.fmi.homeflow.exception.user_exception.UserNotFoundException;
import com.fmi.homeflow.model.dto.user.UserDetailsDto;
import com.fmi.homeflow.model.user.UserEntity;
import com.fmi.homeflow.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

import static com.fmi.homeflow.utility.PlatformConstants.GET_USER_ROUTE;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public UserEntity getUserByUsername(String username) {
        return usersRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));
    }

    public URI createUser(UserEntity userEntity) {
        return createUserURI(addUser(userEntity));
    }

    public UserEntity updateUser(String username, UserDetailsDto userDetailsDto) {
        UserEntity existingUserEntity = getUserByUsername(username);
        return updateUserData(existingUserEntity, userDetailsDto);
    }

    public UserEntity updateUserData(UserEntity userEntity) {
        return usersRepository.save(userEntity);
    }

    public void deleteUser(String username) {
        if (!usersRepository.existsByUsername(username)) {
            throw new UserNotFoundException(username);
        }

        usersRepository.deleteByUsername(username);
    }

    public boolean memberIsInFamily(UserEntity userEntity, UUID familyId) {
        UserEntity familyMember = getUserByUsername(userEntity.getUsername());
        return familyMember.getUserFamily().getId().equals(familyId);
    }

    public boolean memberIsInFamily(String username, UUID familyId) {
        UserEntity userEntity = getUserByUsername(username);
        return userEntity.getUserFamily().getId().equals(familyId);
    }

    private UserEntity addUser(UserEntity userEntity) {
        if (usersRepository.existsByUsername(userEntity.getUsername())) {
            throw new UserAlreadyExistsException(userEntity.getUsername());
        }

        return usersRepository.save(userEntity);
    }

    private URI createUserURI(UserEntity userEntity) {
        return URI.create(GET_USER_ROUTE + userEntity.getUsername());
    }

    private UserEntity updateUserData(UserEntity userEntity, UserDetailsDto userDetailsDto) {
        return updateUserData(updateUserWithGivenDetails(userEntity, userDetailsDto));
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
