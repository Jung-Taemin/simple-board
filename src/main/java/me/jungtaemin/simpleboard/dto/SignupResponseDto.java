package me.jungtaemin.simpleboard.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String email;
    private String name;

    public SignupResponseDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
