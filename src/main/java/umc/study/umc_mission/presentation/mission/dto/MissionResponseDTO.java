package umc.study.umc_mission.presentation.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import umc.study.umc_mission.domain.mission.enums.MissionState;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mission(미션) 도메인 응답 DTO 모음.
 *
 * <p>두 화면 응답을 모두 정의: 내 미션 목록 / 지역 도전 가능 미션 목록.
 * 응답에는 페이징 메타(현재 페이지, 총 페이지, 총 개수, 마지막 여부)를 포함한다.</p>
 */
public class MissionResponseDTO {

    /** 내 미션 목록의 한 행. 미션 진행 상태를 함께 포함. */
    @Builder
    @Schema(description = "내 미션 목록의 한 행")
    public record MyMissionItem(

            @Schema(description = "MemberMission PK", example = "100")
            Long memberMissionId,

            @Schema(description = "Mission PK", example = "11")
            Long missionId,

            @Schema(description = "미션 제목", example = "15,000원 이상 결제하기")
            String title,

            @Schema(description = "미션 설명", example = "이 가게에서 15,000원 이상 결제하면 미션 완료!")
            String content,

            @Schema(description = "보상 포인트", example = "500")
            Long reward,

            @Schema(description = "미션 만료 시각 (없으면 영구)")
            LocalDateTime expiredAt,

            @Schema(description = "미션이 등록된 가게 이름", example = "맛있는 김치찌개")
            String storeName,

            @Schema(description = "미션 진행 상태", example = "CHALLENGING")
            MissionState state

    ) {
    }

    /** 내 미션 목록 페이지 응답. */
    @Builder
    @Schema(description = "내 미션 목록 페이지 응답")
    public record MyMissionPage(
            List<MyMissionItem> missions,
            Integer currentPage,
            Integer totalPages,
            Long totalElements,
            Boolean isLast
    ) {
    }

    /** 지역 도전 가능 미션 목록의 한 행. */
    @Builder
    @Schema(description = "지역 도전 가능 미션의 한 행")
    public record RegionMissionItem(

            @Schema(description = "Mission PK", example = "11")
            Long missionId,

            @Schema(description = "미션 제목")
            String title,

            @Schema(description = "미션 설명")
            String content,

            @Schema(description = "보상 포인트")
            Long reward,

            @Schema(description = "미션 만료 시각")
            LocalDateTime expiredAt,

            @Schema(description = "Store PK")
            Long storeId,

            @Schema(description = "가게 이름")
            String storeName

    ) {
    }

    /** 지역 미션 목록 페이지 응답. */
    @Builder
    @Schema(description = "지역 미션 목록 페이지 응답")
    public record RegionMissionPage(
            List<RegionMissionItem> missions,
            Integer currentPage,
            Integer totalPages,
            Long totalElements,
            Boolean isLast
    ) {
    }
}
