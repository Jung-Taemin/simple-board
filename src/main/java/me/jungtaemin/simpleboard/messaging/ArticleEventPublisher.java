package me.jungtaemin.simpleboard.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ArticleEventPublisher {

    private final KafkaTemplate<String, Object> kafka;

    public void publishCreated(Long id, String title, String author) {
        kafka.send("article.created", id.toString(),
                new ArticleCreatedEvent(id, title, author, Instant.now()));
    }
    public void publishUpdated(Long id, String title, String author) {
        kafka.send("article.updated", id.toString(),
                new ArticleUpdatedEvent(id, title, author, Instant.now()));
    }
    public void publishDeleted(Long id, String author) {
        kafka.send("article.deleted", id.toString(),
                new ArticleDeletedEvent(id, author, Instant.now()));
    }
}
