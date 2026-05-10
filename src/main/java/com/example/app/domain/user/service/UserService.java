package com.example.app.domain.user.service;

import com.example.app.domain.user.dto.request.UserRequest;
import com.example.app.domain.user.dto.response.UserResponse;
import com.example.app.domain.user.entity.User;
import com.example.app.domain.user.repository.UserRepository;
import com.example.app.global.exception.BusinessException;
import com.example.app.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse.Profile signUp(UserRequest.SignUp request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();

        return UserResponse.Profile.from(userRepository.save(user));
    }

    public UserResponse.Profile getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.Profile.from(user);
    }

    @Transactional
    public UserResponse.Profile updateProfile(Long userId, UserRequest.UpdateProfile request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.updateProfile(request.getNickname(), request.getProfileImage());
        return UserResponse.Profile.from(user);
    }

    @Transactional
    public void updatePassword(Long userId, UserRequest.UpdatePassword request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
