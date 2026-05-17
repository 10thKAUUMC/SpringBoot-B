package com.example.umc10th.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

public class MemberResDTO {

    @Schema(description = "홈 화면 응답")
    public record HomeResponse(
            @Schema(description = "회원 ID", example = "1")
            Long memberId,
            @Schema(description = "회원 이름", example = "kim")
            String name,
            @Schema(description = "현재 선택된 지역", example = "마포구")
            String region,
            @Schema(description = "보유 포인트", example = "999999")
            Long point,
            @Schema(description = "완료한 미션 수", example = "7")
            Long completedMissionCount,
            @Schema(description = "홈 화면 목표 미션 수", example = "10")
            Long targetMissionCount,
            @Schema(description = "도전 가능한 미션 목록")
            List<HomeMissionResponse> missions,
            @Schema(description = "다음 페이지 존재 여부", example = "true")
            Boolean hasNext
    ) {
    }

    @Schema(description = "홈 화면 미션 요약 응답")
    public record HomeMissionResponse(
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
            Integer point
    ) {
    }

    @Schema(description = "회원 가입 응답")
    public record JoinResponse(
            @Schema(description = "회원 ID", example = "1")
            Long memberId,
            @Schema(description = "회원 이름", example = "kim")
            String name,
            @Schema(description = "이메일", example = "kim@example.com")
            String email
    ) {
    }

    @Schema(description = "마이페이지 응답")
    public record MyPageResponse(
            @Schema(description = "회원 ID", example = "1")
            Long memberId,
            @Schema(description = "닉네임", example = "nickname012")
            String nickname,
            @Schema(description = "이메일", example = "kim@example.com")
            String email,
            @Schema(description = "휴대폰 번호", example = "010-1234-5678")
            String phone,
            @Schema(description = "휴대폰 인증 여부", example = "false")
            Boolean phoneVerified,
            @Schema(description = "보유 포인트", example = "1000")
            Long point,
            @Schema(description = "작성한 리뷰 수", example = "3")
            Long reviewCount,
            @Schema(description = "완료한 미션 수", example = "5")
            Long completedMissionCount
    ) {
    }
}
