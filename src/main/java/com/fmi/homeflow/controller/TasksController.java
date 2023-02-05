package com.fmi.homeflow.controller;

import com.fmi.homeflow.model.dto.task.TaskDto;
import com.fmi.homeflow.service.task.TaskFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.fmi.homeflow.utility.PlatformConstants.GET_TASK_ROUTE;

@RestController
@RequestMapping("api/tasks")
@AllArgsConstructor
public class TasksController {

    private final TaskFacade taskFacade;

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskFacade.getTaskById(id));
    }

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, #id)")
    @GetMapping("/family/{id}")
    public ResponseEntity<List<TaskDto>> getTasksInFamily(@PathVariable UUID id) {
        return ResponseEntity.ok(taskFacade.getTasksInFamily(id));
    }

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, #task.getFamilyId())")
    @PostMapping
    public ResponseEntity<Void> addTask(@RequestBody TaskDto task) {
        taskFacade.addTask(task);
        return ResponseEntity.created(URI.create(GET_TASK_ROUTE + task.getId())).build();
    }

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, #task.getFamilyId())")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable UUID id, @RequestBody TaskDto task) {
        task.setId(id);
        taskFacade.updateTask(task);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, @taskService.getTaskById(#id))")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchTask(@PathVariable UUID id, @RequestBody TaskDto task) {
        task.setId(id);
        taskFacade.patchTask(task);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@usersService.memberIsInFamily(principal.username, @taskService.getTaskById(#id).familyEntity().id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskFacade.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

}
