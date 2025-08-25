package me.jungtaemin.simpleboard.dto;

public record ArticleResponseDto(
        Long id,
        String title,
        String content,
        String author
){}
