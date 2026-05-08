package com.example.demo.controller;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public TaskResponse create(@RequestBody TaskRequest request) {
        return service.createTask(request);
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable UUID id) {
        return service.getTask(id);
    }

    @GetMapping
    public List<Task> getAll() {
        return service.getAllTasks();
    }
}