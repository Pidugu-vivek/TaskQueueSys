package com.example.demo.dto;

import com.example.demo.model.TaskType;
import lombok.Data;

@Data
public class TaskRequest {
    private TaskType type;
    private String payload;
}