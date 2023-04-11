package com.fmi.homeflow.service.family;

import com.fmi.homeflow.model.dto.family.FamilyDto;
import com.fmi.homeflow.model.family.FamilyEntity;
import com.fmi.homeflow.model.user.UserEntity;
import com.fmi.homeflow.service.user.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

import static com.fmi.homeflow.model.user.Role.FAMILY_OWNER;

@Service
@AllArgsConstructor
public class FamiliesFacade {

    private final FamiliesService familiesService;
    private final UsersService usersService;

    public FamilyEntity getFamily(UUID id) {
        return familiesService.getFamilyById(id);
    }

    public URI addFamily(FamilyDto createFamilyRequest) {
        FamilyEntity familyEntity = familiesService.createFamily(createFamilyRequest.getName());

        for (String memberName : createFamilyRequest.getMembersList()) {
            addFamilyOwner(memberName, familyEntity.getId());
        }

        return familiesService.createFamilyURI(familyEntity.getId());
    }

    public void addFamilyOwner(String username, UUID familyId) {
        UserEntity userEntity = usersService.getUserByUsername(username);
        UserEntity updatedUserEntity = familiesService.addMemberToFamily(userEntity, familyId);
        updatedUserEntity.setRole(FAMILY_OWNER);
        usersService.updateUserData(updatedUserEntity);
    }

    public void addMemberToFamily(String username, UUID familyId) {
        UserEntity userEntity = usersService.getUserByUsername(username);
        UserEntity updatedUserEntity = familiesService.addMemberToFamily(userEntity, familyId);
        usersService.updateUserData(updatedUserEntity);
    }

    public void removeMemberFromFamily(String username, UUID familyId) {
        usersService.updateUserData(
            familiesService.removeMemberFromFamily(
                usersService.getUserByUsername(username),
                familyId
            )
        );
    }

    public void deleteFamily(UUID id) {
        familiesService.deleteFamily(id);
    }

}
