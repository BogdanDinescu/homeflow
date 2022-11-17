package com.fmi.homeflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private UUID taskId;
    private String name;
    private UUID assignee;
    private State state;
    private UUID familyId;

}
