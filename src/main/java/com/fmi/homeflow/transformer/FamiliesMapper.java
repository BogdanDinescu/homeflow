package com.fmi.homeflow.transformer;

import com.fmi.homeflow.model.family.FamilyEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class FamiliesMapper {

    public FamilyEntity mapToFamilyEntity(String familyName) {
        return FamilyEntity.builder()
            .name(familyName)
            .build();
    }

}
