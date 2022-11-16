package com.fmi.homeflow.controller;

import com.fmi.homeflow.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        return ResponseEntity.ok(new Task(id));
    }
}
