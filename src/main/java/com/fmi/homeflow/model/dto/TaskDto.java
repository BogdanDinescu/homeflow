package com.fmi.homeflow.model.dto;

import com.fmi.homeflow.model.State;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class TaskDto {

    private String name;

    private State state;

}
