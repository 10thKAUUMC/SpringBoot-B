package com.example.umc10th.domain.user.dto;

public class UserResDTO {

    public record SignUpResultDTO(
            Long userId,
            String nickname

    ) {}

    public record ChangeRegionResultDTO(
            Long regionId
    ) {}

    public record MyPageDTO(
        String name,
        String email,
        Long totalPoint
    ) {}
}
