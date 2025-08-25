package me.jungtaemin.simpleboard.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishArticleCreated(ArticleCreatedEvent event) {
        kafkaTemplate.send("article.created", String.valueOf(event.getId()), event);
    }
}
