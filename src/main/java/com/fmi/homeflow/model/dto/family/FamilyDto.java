package com.fmi.homeflow.model.dto.family;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class FamilyDto {

    @NotNull
    @NotEmpty
    private String name;
    private Set<String> membersList;

}
