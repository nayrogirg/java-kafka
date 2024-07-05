package org.example.javakafka.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javakafka.entity.Message;
import org.example.javakafka.repository.MessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageConsumerService {

    private final MessageRepository messageRepository;

    /**
     * Listens to messages from the specified Kafka topic and saves them to the database.
     *
     * @param message The message received from Kafka.
     */
    @KafkaListener(topics = "topic", groupId = "group-id")
    public void listen(Message message) {

        log.info("Received message: {}", message);

        messageRepository.save(message);

        log.info("Message saved to the database: {}", message);
    }
}
