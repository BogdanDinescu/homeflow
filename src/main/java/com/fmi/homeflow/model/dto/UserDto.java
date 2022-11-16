package com.fmi.homeflow.model.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor()
public class UserDto {

    private UUID id;
    private String name;

}
