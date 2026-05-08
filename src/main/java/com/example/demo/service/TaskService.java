package com.example.demo.service;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.queue.TaskProducer;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final TaskProducer producer;

    public TaskResponse createTask(TaskRequest request) {

        Task task = Task.builder()
                .type(request.getType())
                .payload(request.getPayload())
                .status(TaskStatus.PENDING)
                .retries(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(task);

        // send to queue
        producer.sendTask(task.getId());

        return TaskResponse.builder()
                .id(task.getId())
                .status(task.getStatus())
                .build();
    }

    public Task getTask(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }
}