package com.fmi.homeflow.transformer;

import com.fmi.homeflow.model.dto.task.TaskDto;
import com.fmi.homeflow.model.task.TaskEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class TasksMapper {

    public TaskDto mapToTaskDto(TaskEntity taskEntity) {
        return TaskDto.builder()
            .id(taskEntity.getId())
            .name(taskEntity.getName())
            .state(taskEntity.getState())
            .familyId(taskEntity.getFamilyEntity().getId())
            .assigneeName(taskEntity.getAssignee() != null ? taskEntity.getAssignee().getUsername() : "")
            .build();
    }

}
