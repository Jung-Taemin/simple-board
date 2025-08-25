package me.jungtaemin.simpleboard.controller;

import lombok.RequiredArgsConstructor;
import me.jungtaemin.simpleboard.dto.LoginRequestDto;
import me.jungtaemin.simpleboard.dto.LoginResponseDto;
import me.jungtaemin.simpleboard.dto.SignupRequestDto;
import me.jungtaemin.simpleboard.dto.SignupResponseDto;
import me.jungtaemin.simpleboard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = userService.login(requestDto);
        return ResponseEntity.ok(response);
    }
}
