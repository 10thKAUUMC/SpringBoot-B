package com.example.umc10th.domain.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class MissionReqDTO {

    @Schema(description = "미션 상태 변경 요청")
    public record UpdateMissionStatusRequest(
            @Schema(description = "변경할 미션 상태", example = "SUCCESS_REQUESTED", allowableValues = {
                    "IN_PROGRESS",
                    "COMPLETED",
                    "SUCCESS_REQUESTED"
            })
            String status
    ) {
    }
}
