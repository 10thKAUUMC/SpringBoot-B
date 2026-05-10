package umc.study.umc_mission.presentation.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.study.umc_mission.domain.mission.enums.MissionState;
import umc.study.umc_mission.domain.mission.exception.MissionSuccessCode;
import umc.study.umc_mission.domain.mission.service.MissionService;
import umc.study.umc_mission.global.apiPayload.ApiResponse;
import umc.study.umc_mission.presentation.mission.dto.MissionResponseDTO;

/**
 * 미션(Mission) 도메인 REST 컨트롤러.
 *
 * <p>두 엔드포인트:</p>
 * <ul>
 *   <li>{@code GET /api/v1/users/{memberId}/missions?state=&page=&size=} — 내 미션 목록</li>
 *   <li>{@code GET /api/v1/regions/{regionId}/missions?page=&size=} — 지역 도전 가능 미션</li>
 * </ul>
 *
 * <p>두 엔드포인트의 자원 소속이 달라 컨트롤러 하나에서 {@code @RequestMapping}을 단일 prefix로
 * 잡지 않고, 메서드 단에서 전체 경로를 명시한다.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "Mission", description = "미션(Mission) 도메인 API — 내 미션 목록 / 지역 도전 가능 미션")
public class MissionController {

    private final MissionService missionService;

    /**
     * 내 진행중/완료 미션 목록 조회 (페이징).
     *
     * <p>요청 예시:</p>
     * <pre>{@code
     * GET /api/v1/users/1/missions?state=CHALLENGING&page=0&size=10
     * }</pre>
     */
    @GetMapping("/api/v1/users/{memberId}/missions")
    @Operation(summary = "내 미션 목록 조회",
            description = "회원의 미션 참여 기록을 상태(CHALLENGING/COMPLETED)별로 페이징해 반환한다.")
    public ApiResponse<MissionResponseDTO.MyMissionPage> getMyMissions(
            @PathVariable Long memberId,
            @Parameter(description = "필터링 상태 (기본 CHALLENGING)")
            @RequestParam(defaultValue = "CHALLENGING") MissionState state,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        MissionResponseDTO.MyMissionPage result = missionService.getMyMissions(memberId, state, pageable);
        return ApiResponse.onSuccess(MissionSuccessCode.GET_MY_MISSIONS, result);
    }

    /**
     * 지역(홈 화면 선택 지역) 기준 도전 가능 미션 목록 조회 (페이징).
     */
    @GetMapping("/api/v1/regions/{regionId}/missions")
    @Operation(summary = "지역 도전 가능 미션 목록",
            description = "선택한 지역에 속한 가게들의 만료되지 않은 미션을 페이징해 반환한다.")
    public ApiResponse<MissionResponseDTO.RegionMissionPage> getMissionsByRegion(
            @PathVariable Long regionId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        MissionResponseDTO.RegionMissionPage result = missionService.getMissionsByRegion(regionId, pageable);
        return ApiResponse.onSuccess(MissionSuccessCode.GET_REGION_MISSIONS, result);
    }
}
