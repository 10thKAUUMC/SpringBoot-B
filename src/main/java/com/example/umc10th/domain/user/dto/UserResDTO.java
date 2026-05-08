package com.example.umc10th.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResDTO {

    public record SignUpResultDTO(
            Long userId,
            String nickname

    ) {}

    public record ChangeRegionResultDTO(
            Long regionId
    ) {}

    @Builder
    @Getter
    @AllArgsConstructor
    public static class MyPageDTO {
        String name;
        String email;
        Long totalPoint;
    }
}
