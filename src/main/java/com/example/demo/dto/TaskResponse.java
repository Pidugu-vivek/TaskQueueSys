package com.example.demo.dto;

import com.example.demo.model.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TaskResponse {
    private UUID id;
    private TaskStatus status;
}