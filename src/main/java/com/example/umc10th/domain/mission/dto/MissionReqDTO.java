package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MissionReqDTO {

    public record CreateMission(
            @NotNull(message = "마감기한은 필수입니다.")
            LocalDateTime deadline,
            @NotNull(message = "미션 성공 포인트는 필수입니다.")
            Long point,
            @NotBlank(message = "조건은 빈칸일 수 있습니다.")
            String conditional
    ) {}

    public record InProgressMissionDTO(
            Long userId
    ) {}
}
