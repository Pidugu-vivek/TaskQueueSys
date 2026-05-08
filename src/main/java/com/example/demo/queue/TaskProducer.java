package com.example.demo.queue;

import com.example.demo.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendTask(UUID taskId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE, taskId.toString());
    }
}