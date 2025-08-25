package me.jungtaemin.simpleboard.dto;

public record LoginResponseDto(
        String token,
        String email
) {}
