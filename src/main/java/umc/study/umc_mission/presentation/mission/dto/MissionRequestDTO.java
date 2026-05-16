package umc.study.umc_mission.presentation.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Mission(미션) 도메인 요청 DTO 모음.
 *
 * <p>외부 클래스 한 곳에 중첩 record로 모아 도메인 네임스페이스 역할.</p>
 */
public class MissionRequestDTO {

    /**
     * 7주차 미션 — "내 진행중 미션 조회"용 요청.
     *
     * <p>워크북 지시사항: <b>사용자 ID는 Request Body에서 받기 (하드코딩 X)</b>.
     * JWT 도입(9주차) 전까지 임시 운영. POST를 쓰는 이유는 HTTP 표준상 GET 요청에는
     * 의미 있는 Body를 두지 않기 때문.</p>
     */
    @Schema(description = "내 진행중 미션 조회 요청")
    public record MyInProgressRequest(

            @Schema(description = "조회 대상 회원 PK", example = "1")
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId

    ) {
    }
}
