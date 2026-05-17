package com.example.umc10th.domain.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class MissionReqDTO {

    @Schema(description = "진행중인 내 미션 조회 요청")
    public record InProgressMissionListRequest(
            @Schema(description = "회원 ID", example = "1")
            @NotNull(message = "회원 ID는 필수입니다.")
            @Positive(message = "회원 ID는 양수여야 합니다.")
            Long memberId,

            @Schema(description = "페이지 번호. 0부터 시작합니다.", example = "0", defaultValue = "0")
            @PositiveOrZero(message = "페이지 번호는 0 이상이어야 합니다.")
            Integer page,

            @Schema(description = "한 페이지에 조회할 미션 개수", example = "10", defaultValue = "10")
            @Min(value = 1, message = "조회 개수는 1 이상이어야 합니다.")
            @Max(value = 50, message = "조회 개수는 50 이하여야 합니다.")
            Integer size
    ) {
    }

    @Schema(description = "미션 상태 변경 요청")
    public record UpdateMissionStatusRequest(
            @Schema(description = "변경할 미션 상태", example = "SUCCESS_REQUESTED", allowableValues = {
                    "IN_PROGRESS",
                    "COMPLETED",
                    "SUCCESS_REQUESTED"
            })
            @NotNull(message = "미션 상태는 필수입니다.")
            String status
    ) {
    }
}
