package com.example.app.domain.user.dto.response;

import com.example.app.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserResponse {

    @Getter
    @Builder
    public static class Profile {
        private Long userId;
        private String email;
        private String nickname;
        private String profileImage;
        private LocalDateTime createdAt;

        public static Profile from(User user) {
            return Profile.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .profileImage(user.getProfileImage())
                    .createdAt(user.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class TokenInfo {
        private String accessToken;
        private String refreshToken;
        private Long userId;
        private String nickname;
    }
}
