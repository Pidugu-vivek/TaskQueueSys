package com.example.demo.worker;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskWorker {

    private final TaskRepository repository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void process(String taskIdStr) throws InterruptedException {

        UUID taskId = UUID.fromString(taskIdStr);

        Task task = repository.findById(taskId).orElseThrow();

        task.setStatus(TaskStatus.IN_PROGRESS);
        repository.save(task);

        try {
            Thread.sleep(3000); // simulate work

            task.setStatus(TaskStatus.SUCCESS);

        } catch (Exception e) {

            task.setRetries(task.getRetries() + 1);
            task.setErrorMessage(e.getMessage());

            if (task.getRetries() >= 3) {
                task.setStatus(TaskStatus.FAILED);
            } else {
                throw e;
            }
        }

        task.setUpdatedAt(LocalDateTime.now());
        repository.save(task);
    }
}