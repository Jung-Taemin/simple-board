package me.jungtaemin.simpleboard.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@Slf4j
@Configuration
@KafkaListener(id = "article-consumer", topics = {"article.created", "article.updated", "article.deleted"})
public class ArticleEventListener {

    @KafkaHandler
    public void onCreated(ArticleCreatedEvent e,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("[Kafka] {} => created id={}, title={}, author={}", topic, e.id(), e.title(), e.author());
    }

    @KafkaHandler
    public void onUpdated(ArticleUpdatedEvent e,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("[Kafka] {} => updated id={}, title={}, author={}", topic, e.id(), e.title(), e.author());
    }

    @KafkaHandler
    public void onDeleted(ArticleUpdatedEvent e,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("[Kafka] {} => deleted id={}, author={}", topic, e.id(), e.author());
    }

    @KafkaHandler
    public void onUnknown(Object unknown, ConsumerRecord<?, ?> record) {
        log.warn("Unknown message type: {} from topic={}", unknown, record.topic());
    }
}
