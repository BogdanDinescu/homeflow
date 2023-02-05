package com.fmi.homeflow.repository;

import com.fmi.homeflow.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    void deleteByUsername(String username);

    Boolean existsByUsername(String username);

}
