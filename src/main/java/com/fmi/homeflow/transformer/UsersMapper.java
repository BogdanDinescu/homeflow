package com.fmi.homeflow.transformer;

import com.fmi.homeflow.model.dto.user.CreateUserRequest;
import com.fmi.homeflow.model.dto.user.UserDetailsDto;
import com.fmi.homeflow.model.user.Role;
import com.fmi.homeflow.model.user.UserEntity;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UsersMapper {

    public UserDetailsDto mapToUserDetailsDto(UserEntity userEntity) {
        return UserDetailsDto.builder()
            .id(userEntity.getId())
            .name(userEntity.getUsername())
            .firstName(userEntity.getFirstName())
            .lastName(userEntity.getLastName())
            .email(userEntity.getEmail())
            .phone(userEntity.getPhone())
            .familyId(userEntity.getUserFamilyEntity() != null ? userEntity.getUserFamilyEntity().getId() : null)
            .build();
    }

    public UserEntity mapToUserEntity(CreateUserRequest createUserRequest, final PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
            .username(createUserRequest.getName())
            .password(passwordEncoder.encode(createUserRequest.getPassword()))
            .role(Role.MEMBER)
            .build();
    }

}
