package me.jungtaemin.simpleboard.messaging;

import java.time.Instant;

public record ArticleDeletedEvent(Long id, String author, Instant at) {
}
