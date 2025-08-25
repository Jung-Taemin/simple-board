package me.jungtaemin.simpleboard.dto;

import jakarta.validation.constraints.NotBlank;

public record ArticleRequestDto(
        @NotBlank String title,
        @NotBlank String content
) {}
