package me.jungtaemin.simpleboard.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean NewTopic articleCreated() {
        return TopicBuilder.name("article.created").partitions(3).replicas(1).build();
    }
    @Bean NewTopic articleUpdated() {
        return TopicBuilder.name("article.updated").partitions(3).replicas(1).build();
    }
    @Bean NewTopic articleDeleted() {
        return TopicBuilder.name("article.deleted").partitions(3).replicas(1).build();
    }
    @Bean NewTopic dlt() {
        return TopicBuilder.name("article.DLT").partitions(3).replicas(1).build();
    }
}
