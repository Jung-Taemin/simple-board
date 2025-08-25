package me.jungtaemin.simpleboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
}
