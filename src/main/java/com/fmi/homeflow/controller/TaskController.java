package com.fmi.homeflow.controller;

import com.fmi.homeflow.model.Task;
import com.fmi.homeflow.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static com.fmi.homeflow.utility.UserConstants.GET_USER_ROUTE;

@RestController
@RequestMapping("api/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PreAuthorize("@familyService.memberIsInFamily(principal.username, #task.getFamilyId())")
    @PostMapping
    public ResponseEntity<Void> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return ResponseEntity.created(URI.create(GET_USER_ROUTE + task.getId())).build();
    }

    @PreAuthorize("@familyService.memberIsInFamily(principal.username, #task.getFamilyId())")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable UUID id, @RequestBody Task task) {
        task.setId(id);
        taskService.updateTask(task);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@familyService.memberIsInFamily(principal.username, #task.getFamilyId())")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchTask(@PathVariable UUID id, @RequestBody Task task) {
        task.setId(id);
        taskService.patchTask(task);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@familyService.memberIsInFamily(principal.username, #task.getFamilyId())")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

}
