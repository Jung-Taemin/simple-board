package me.jungtaemin.simpleboard.messaging;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleCreatedEvent {
    private Long id;
    private String title;
    private String author;
    private Instant createdAt;
}
