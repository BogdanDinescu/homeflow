package com.fmi.homeflow.service.family;

import com.fmi.homeflow.exception.InvalidDataException;
import com.fmi.homeflow.exception.user_exception.FamilyNotFoundException;
import com.fmi.homeflow.model.family.FamilyEntity;
import com.fmi.homeflow.model.user.UserEntity;
import com.fmi.homeflow.repository.FamilyRepository;
import com.fmi.homeflow.transformer.FamiliesMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

import static com.fmi.homeflow.utility.PlatformConstants.GET_FAMILY_ROUTE;

@Service
@AllArgsConstructor
public class FamiliesService {

    private final FamilyRepository familyRepository;
    private final FamiliesMapper familiesMapper;

    public FamilyEntity getFamilyById(UUID id) {
        return familyRepository.findById(id)
            .orElseThrow(() -> new FamilyNotFoundException(id));
    }

    public FamilyEntity createFamily(String familyName) {
        return familyRepository.save(familiesMapper.mapToFamilyEntity(familyName));
    }

    public URI createFamilyURI(UUID id) {
        return URI.create(GET_FAMILY_ROUTE + id);
    }

    public UserEntity addMemberToFamily(UserEntity userEntity, UUID familyId) {
        FamilyEntity familyEntity = getFamilyById(familyId);
        userEntity.setUserFamilyEntity(familyEntity);
        return userEntity;
    }

    public UserEntity removeMemberFromFamily(UserEntity userEntity, UUID familyId) {
        if (userEntity.getUserFamilyEntity().getId().equals(familyId)) {
            throw new InvalidDataException("familyId");
        }

        userEntity.setUserFamilyEntity(null);
        return userEntity;
    }

    public void deleteFamily(UUID id) {
        if (getFamilyById(id) != null) {
            familyRepository.deleteById(id);
        }
    }

}
