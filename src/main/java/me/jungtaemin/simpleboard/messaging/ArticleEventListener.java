package me.jungtaemin.simpleboard.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class ArticleEventListener {

    @KafkaListener(topics = "article.created", groupId = "simple-board")
    public void onArticleCreated(ArticleCreatedEvent event) {
        log.info("[Kafka] Consumed article.created: id={}, title={}, author={}",
                event.getId(), event.getTitle(), event.getAuthor());
    }
}
