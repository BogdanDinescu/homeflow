package com.fmi.homeflow.service;

import com.fmi.homeflow.exception.user_exception.FamilyNotFoundException;
import com.fmi.homeflow.model.Family;
import com.fmi.homeflow.model.User;
import com.fmi.homeflow.repository.FamilyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserService userService;

    public Family createFamily(String name, Set<User> members) {
        Family family = Family.builder().name(name).membersList(members).build();
        return familyRepository.save(family);
    }

    public boolean familyExistsById(UUID id) {
        return familyRepository.existsById(id);
    }

    public boolean familyExists(Family family) {
        return familyExistsById(family.getId());
    }

    public Family getFamilyById(UUID id) {
        Optional<Family> familyOptional = familyRepository.findById(id);
        if (familyOptional.isEmpty()) {
            throw new FamilyNotFoundException(id);
        }
        return familyOptional.get();
    }


    public void addMemberToFamily(String username, UUID familyId) {
        User user = userService.getUserByUsername(username);
        Family family = getFamilyById(familyId);
        user.setUserFamily(family);
        userService.upsertUser(user);
    }

    public void removeMemberFromFamily(String username, UUID familyId) {
        User user = userService.getUserByUsername(username);
        Family family = getFamilyById(familyId);
        user.setUserFamily(null);
        userService.upsertUser(user);
    }

    public boolean memberIsInFamily(User user, UUID familyId) {
        User user1 = userService.getUserByUsername(user.getUsername());
        return user1.getUserFamily().getId().equals(familyId);
    }

    public boolean memberIsInFamily(String username, UUID familyId) {
        User user = userService.getUserByUsername(username);
        return user.getUserFamily().getId().equals(familyId);
    }

    public void deleteFamily(UUID id) {
        familyRepository.deleteById(id);
    }
}
