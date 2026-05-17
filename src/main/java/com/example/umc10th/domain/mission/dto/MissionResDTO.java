package com.example.umc10th.domain.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

public class MissionResDTO {

    @Schema(description = "미션 목록 응답")
    public record MissionListResponse(
            @Schema(description = "미션 목록")
            List<MissionSummaryResponse> missions,
            @Schema(description = "조회한 미션 상태. status 생략 시 ALL", example = "IN_PROGRESS")
            String status,
            @Schema(description = "다음 페이지 존재 여부", example = "true")
            Boolean hasNext
    ) {
    }

    @Schema(description = "미션 요약 응답")
    public record MissionSummaryResponse(
            @Schema(description = "회원 미션 ID", example = "1")
            Long userMissionId,
            @Schema(description = "미션 ID", example = "10")
            Long missionId,
            @Schema(description = "가게 ID", example = "15403567")
            Long storeId,
            @Schema(description = "가게 이름", example = "맛있는 식당")
            String storeName,
            @Schema(description = "가게 설명 또는 카테고리", example = "중식당")
            String storeDescription,
            @Schema(description = "미션 내용", example = "리뷰 작성하기")
            String content,
            @Schema(description = "미션 마감일", example = "2026-05-31")
            LocalDate deadline,
            @Schema(description = "미션 보상 포인트", example = "500")
            Integer point,
            @Schema(description = "미션 상태", example = "IN_PROGRESS")
            String status,
            @Schema(description = "화면에 표시할 상태 문구", example = "진행중")
            String statusLabel,
            @Schema(description = "미션 카드 버튼 문구", example = "리뷰 남기기")
            String actionLabel
    ) {
    }

    @Schema(description = "미션 상태 변경 응답")
    public record UpdateMissionStatusResponse(
            @Schema(description = "회원 미션 ID", example = "1")
            Long userMissionId,
            @Schema(description = "변경된 미션 상태", example = "SUCCESS_REQUESTED")
            String status
    ) {
    }
}
