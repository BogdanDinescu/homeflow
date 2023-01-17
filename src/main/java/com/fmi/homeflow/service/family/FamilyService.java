package com.fmi.homeflow.service.family;

import com.fmi.homeflow.exception.InvalidDataException;
import com.fmi.homeflow.exception.user_exception.FamilyNotFoundException;
import com.fmi.homeflow.model.Family;
import com.fmi.homeflow.model.UserEntity;
import com.fmi.homeflow.repository.FamilyRepository;
import com.fmi.homeflow.repository.UserRepository;
import com.fmi.homeflow.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Family createFamily(String name, Set<String> members) {
        Family familyToBeInserted = Family.builder().name(name).build();
        Family family = familyRepository.save(familyToBeInserted);

        for (String username : members) {
            addMemberToFamily(username, family.getId());
        }

        return family;
    }

    public Family getFamilyById(UUID id) {
        return familyRepository.findById(id)
                .orElseThrow(() -> new FamilyNotFoundException(id));
    }


    public void addMemberToFamily(String username, UUID familyId) {
        UserEntity userEntity = userService.getUserByUsername(username);
        Family family = getFamilyById(familyId);
        userEntity.setUserFamily(family);
        userService.updateUser(userEntity);
    }

    /**
     * Removes user from family, by using username and familyId.
     * Throws {@link InvalidDataException} if given familyId is different from the one of the user.
     *
     * @param username name used by the user on the platform, used to identify him for removal.
     * @param familyId id of the family from which we want to remove the given user by username.
     */
    public void removeMemberFromFamily(String username, UUID familyId) {
        UserEntity userEntity = userService.getUserByUsername(username);

        if (userEntity.getUserFamily().getId().equals(familyId)) {
            throw new InvalidDataException("familyId");
        }

        userEntity.setUserFamily(null);
        userService.updateUser(userEntity);
    }

    public boolean memberIsInFamily(UserEntity userEntity, UUID familyId) {
        UserEntity familyMember = userService.getUserByUsername(userEntity.getUsername());
        return familyMember.getUserFamily().getId().equals(familyId);
    }

    public boolean memberIsInFamily(String username, UUID familyId) {
        UserEntity userEntity = userService.getUserByUsername(username);
        return userEntity.getUserFamily().getId().equals(familyId);
    }

    public void deleteFamily(UUID id) {
        familyRepository.deleteById(id);
    }
}
