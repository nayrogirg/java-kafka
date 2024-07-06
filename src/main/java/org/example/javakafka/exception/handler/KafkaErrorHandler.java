package org.example.javakafka.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

@Slf4j
public class KafkaErrorHandler implements CommonErrorHandler {

    /**
     * Handles exceptions thrown by non-batch Kafka message listeners.
     *
     */
    @Override
    public void handleOtherException(final Exception exception, final Consumer<?, ?> consumer, final MessageListenerContainer container, final boolean batchListener) {
        handle(exception, consumer);
    }

    /**
     * Handles exceptions thrown by batch Kafka message listeners.
     *
     */
    @Override
    public boolean handleOne(final Exception exception, final ConsumerRecord<?, ?> record, final Consumer<?, ?> consumer, final MessageListenerContainer container) {
        handle(exception, consumer);
        return true;
    }

    /**
     * Handles the exception by logging it and taking appropriate actions.
     *
     */
    void handle(Exception exception, Consumer<?, ?> consumer) {
        log.error("Exception thrown", exception);

        if (exception instanceof RecordDeserializationException ex) {

            log.error("Deserialization exception for topic-partition: {}, offset: {}",
                    ex.topicPartition(), ex.offset(), exception);

            // Seek to the next offset and commit to skip the problematic record
            consumer.seek(ex.topicPartition(), ex.offset() + 1L);
            consumer.commitSync();
            log.info("Skipped offset {} in topic-partition {}", ex.offset(), ex.topicPartition());
        } else {
            log.error("Exception not handled", exception);
        }
    }
}
