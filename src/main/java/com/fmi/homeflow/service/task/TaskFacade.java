package com.fmi.homeflow.service.task;

import com.fmi.homeflow.model.Family;
import com.fmi.homeflow.model.Task;
import com.fmi.homeflow.model.UserEntity;
import com.fmi.homeflow.model.dto.TaskDto;
import com.fmi.homeflow.service.family.FamilyService;
import com.fmi.homeflow.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskFacade {

    private final TaskService taskService;
    private final FamilyService familyService;
    private final UserService userService;

    public TaskDto getTaskById(UUID id) {
        Task task = taskService.getTaskById(id);
        return TaskDto.builder()
            .id(task.getId())
            .name(task.getName())
            .state(task.getState())
            .assigneeName(task.getAssignee().getUsername())
            .familyId(task.getFamily().getId())
            .build();
    }

    public List<TaskDto> getTasksInFamily(UUID id) {
        Family family = familyService.getFamilyById(id);
        List<Task> tasks = taskService.getTasksInFamily(family);
        return tasks.stream()
                .map(task -> TaskDto.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .state(task.getState())
                        .familyId(id)
                        .assigneeName(task.getAssignee()!=null ? task.getAssignee().getUsername() : "")
                        .build())
                .toList();
    }

    public void addTask(TaskDto taskDto) {
        Task task = dtoToTask(taskDto);
        taskService.addTask(task);

    }

    public void updateTask(TaskDto taskDto) {
        Task task = dtoToTask(taskDto);
        taskService.updateTask(task);
    }

    public void patchTask(TaskDto taskDto) {
        Task task = dtoToTask(taskDto);
        taskService.patchTask(task);
    }

    public void deleteTaskById(UUID id) {
        taskService.deleteTaskById(id);
    }

    private Task dtoToTask(TaskDto task) {
        Family family = familyService.getFamilyById(task.getFamilyId());
        UserEntity userEntity = null;
        if (task.getAssigneeName() != null) {
            userEntity = userService.getUserByUsername(task.getAssigneeName());
        }
        return Task.builder()
            .id(task.getId())
            .name(task.getName())
            .state(task.getState())
            .assignee(userEntity)
            .family(family)
            .build();
    }

}
