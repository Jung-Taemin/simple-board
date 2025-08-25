package me.jungtaemin.simpleboard.service;

import lombok.RequiredArgsConstructor;
import me.jungtaemin.simpleboard.config.jwt.TokenProvider;
import me.jungtaemin.simpleboard.domain.User;
import me.jungtaemin.simpleboard.dto.LoginRequestDto;
import me.jungtaemin.simpleboard.dto.LoginResponseDto;
import me.jungtaemin.simpleboard.dto.SignupRequestDto;
import me.jungtaemin.simpleboard.dto.SignupResponseDto;
import me.jungtaemin.simpleboard.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public SignupResponseDto signup(SignupRequestDto requestDto) {
        userRepository.findByEmail(requestDto.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 이메일입니다");
                });

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .name(requestDto.getName())
                .build();

        userRepository.save(user);

        return new SignupResponseDto(user.getEmail(), user.getName());
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = tokenProvider.generateToken(user.getEmail());

        return new LoginResponseDto(token);
    }

}
