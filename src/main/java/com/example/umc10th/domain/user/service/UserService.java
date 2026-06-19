package com.example.umc10th.domain.user.service;

import com.example.umc10th.domain.user.controller.UserController;
import com.example.umc10th.domain.user.converter.UserConverter;
import com.example.umc10th.domain.user.dto.UserReqDTO;
import com.example.umc10th.domain.user.dto.UserResDTO;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.enums.Gender;
import com.example.umc10th.domain.user.exception.UserErrorCode;
import com.example.umc10th.domain.user.exception.UserException;
import com.example.umc10th.domain.user.exception.UserSuccessCode;
import com.example.umc10th.domain.user.repository.UserRepository;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.security.entity.AuthMember;
import com.example.umc10th.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResDTO.SignUpResultDTO signUp(UserReqDTO.SignUpDTO request) {
        String encodedPassword =
                passwordEncoder.encode(request.password());

        User user = User.builder()
                .email(request.email())
                .password(encodedPassword)
                .name(request.nickname())
                .gender(request.gender())
                .birthday(request.birth())
                .address("서울")
                .totalPoint(0L)
                .build();

        User savedUser = userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(
                savedUser.getUserId(),
                savedUser.getEmail()

        );
        return new UserResDTO.SignUpResultDTO(savedUser.getUserId(),accessToken);
    }

    public UserResDTO.MyPageDTO getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        return new UserResDTO.MyPageDTO(
                user.getName(),
                user.getEmail(),
                user.getTotalPoint()
                );
    }
    @Transactional(readOnly = true)
    public UserResDTO.MyPageDTO getMyPageByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        return new UserResDTO.MyPageDTO(
                user.getName(),
                user.getEmail(),
                user.getTotalPoint()
        );
    }
    @Transactional(readOnly = true)
    public UserResDTO.LoginResultDTO login(UserReqDTO.LoginDTO request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(
                user.getUserId(),
                user.getEmail()
        );

        return new UserResDTO.LoginResultDTO(user.getUserId(), accessToken);
    }
}
