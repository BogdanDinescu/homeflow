package com.fmi.homeflow.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    UUID id;
    String name;
    String password;
    Role role;

}
