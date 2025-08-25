package me.jungtaemin.simpleboard.service;

import lombok.RequiredArgsConstructor;
import me.jungtaemin.simpleboard.domain.User;
import me.jungtaemin.simpleboard.dto.SignupRequestDto;
import me.jungtaemin.simpleboard.dto.SignupResponseDto;
import me.jungtaemin.simpleboard.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SignupResponseDto signup(SignupRequestDto requestDto) {
        userRepository.findByEmail(requestDto.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 이메일입니다");
                });

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .build();

        userRepository.save(user);

        return new SignupResponseDto(user.getEmail(), user.getName());
    }
}
