package com.fmi.homeflow.service;

import com.fmi.homeflow.exception.user_exception.FamilyNotFoundException;
import com.fmi.homeflow.exception.user_exception.UserNotFoundException;
import com.fmi.homeflow.model.Family;
import com.fmi.homeflow.repository.FamilyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserService userService;

    /*public Family createFamily(String name, Set<UUID> members) {
        Family family = new Family(generateUUID(), name, members);
        database.put(family.getId(), family);      //   Family savedFamily = familyRepository.save(family);
        return family;
    }

    public boolean familyExistsById(UUID id) {
        return database.containsKey(id);
    }

    public boolean familyExists(Family family) {
        return familyExistsById(family.getId());
    }

    public Family getFamilyById(UUID id) {
        if (familyExistsById(id)) {
            return database.get(id);
        }
        throw new FamilyNotFoundException(id);
    }


    public void addMemberToFamily(UUID userId, UUID familyId) {
        if (!userService.userExistsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        Family family = getFamilyById(familyId);
        family.addMember(userId);
        database.put(familyId, family);
    }

    public void removeMemberFromFamily(UUID userId, UUID familyId) {
        if (!userService.userExistsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        Family family = getFamilyById(familyId);
        family.removeMember(familyId);
        database.put(familyId, family);
    }

    public boolean memberIsInFamily(UUID userId, UUID familyId) {
        if (!userService.userExistsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        Family family = getFamilyById(familyId);
        return family.hasMember(userId);
    }

    public boolean deleteFamily(UUID id) {
        return database.remove(id) != null;
    }*/
}
