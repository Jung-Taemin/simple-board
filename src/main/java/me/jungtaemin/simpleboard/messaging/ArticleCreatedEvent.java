package me.jungtaemin.simpleboard.messaging;

import java.time.Instant;

public record ArticleCreatedEvent(Long id, String title, String author, Instant at) {}