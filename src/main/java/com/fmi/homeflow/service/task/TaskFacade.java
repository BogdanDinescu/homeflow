package com.fmi.homeflow.service.task;

import com.fmi.homeflow.model.dto.task.TaskDto;
import com.fmi.homeflow.model.family.FamilyEntity;
import com.fmi.homeflow.model.task.TaskEntity;
import com.fmi.homeflow.model.user.UserEntity;
import com.fmi.homeflow.service.family.FamiliesService;
import com.fmi.homeflow.service.notifications.NotificationsService;
import com.fmi.homeflow.service.user.UsersService;
import com.fmi.homeflow.transformer.TasksMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskFacade {

    private final TaskService taskService;
    private final FamiliesService familiesService;
    private final UsersService usersService;
    private final TasksMapper tasksMapper;
    private final NotificationsService notificationsService;

    public TaskDto getTaskById(UUID id) {
        return tasksMapper.mapToTaskDto(taskService.getTaskById(id));
    }

    public List<TaskDto> getTasksInFamily(UUID id) {
        FamilyEntity familyEntity = familiesService.getFamilyById(id);
        List<TaskEntity> tasksInFamily = taskService.getTasksInFamily(familyEntity);
        return tasksInFamily.stream()
            .map(tasksMapper::mapToTaskDto)
            .toList();
    }

    public void addTask(TaskDto taskDto) {
        TaskEntity taskEntity = dtoToTask(taskDto);
        taskService.addTask(taskEntity);

    }

    public void updateTask(TaskDto taskDto) {
        TaskEntity taskEntity = dtoToTask(taskDto);
        taskService.updateTask(taskEntity);
    }

    public void patchTask(TaskDto taskDto) {
        TaskEntity taskEntity = dtoToTask(taskDto);
        taskService.patchTask(taskEntity);
    }

    public void deleteTaskById(UUID id) {
        taskService.deleteTaskById(id);
    }

    private TaskEntity dtoToTask(TaskDto task) {
        FamilyEntity familyEntity = familiesService.getFamilyById(task.getFamilyId());
        UserEntity userEntity = null;
        if (task.getAssigneeName() != null) {
            userEntity = usersService.getUserByUsername(task.getAssigneeName());
        }
        return TaskEntity.builder()
            .id(task.getId())
            .name(task.getName())
            .state(task.getState())
            .assignee(userEntity)
            .familyEntity(familyEntity)
            .build();
    }

}
