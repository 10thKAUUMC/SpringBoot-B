package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/missions")
@Tag(name = "Mission", description = "미션 목록 조회 및 미션 상태 변경 API")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping
    @Operation(
            summary = "내 미션 목록 조회",
            description = "미션 탭에서 사용자가 진행 중이거나 완료한 모든 미션을 스크롤 기반으로 조회합니다. status를 생략하면 진행중/완료 미션을 함께 조회합니다."
    )
    public ApiResponse<MissionResDTO.MissionListResponse> getMissions(
            @Parameter(description = "Bearer access token. 임시 구현에서는 Bearer 뒤 숫자를 회원 ID로 사용합니다.", example = "Bearer 1")
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Parameter(description = "지역 필터. 현재 내 미션 목록에서는 선택 값만 전달받고 쿼리 조건에는 사용하지 않습니다.", example = "마포구")
            @RequestParam(required = false) String region,
            @Parameter(description = "미션 상태. IN_PROGRESS, COMPLETED, ALL 사용 가능하며 생략 시 ALL로 처리합니다.", example = "IN_PROGRESS")
            @RequestParam(required = false) String status,
            @Parameter(description = "마지막으로 조회한 회원 미션 ID. 첫 조회 시 생략합니다.", example = "20")
            @RequestParam(required = false) Long lastId,
            @Parameter(description = "한 번에 조회할 미션 개수", example = "10")
            @RequestParam(defaultValue = "10") Integer size
    ) {
        MissionResDTO.MissionListResponse response = missionService.getMyMissions(authorization, status, lastId, size);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{userMissionId}")
    @Operation(
            summary = "미션 상태 변경",
            description = "회원 미션의 상태를 변경합니다. 미션 성공 요청 시 status에 SUCCESS_REQUESTED를 전달합니다."
    )
    public ApiResponse<MissionResDTO.UpdateMissionStatusResponse> updateMissionStatus(
            @Parameter(description = "Bearer access token. 임시 구현에서는 Bearer 뒤 숫자를 회원 ID로 사용합니다.", example = "Bearer 1")
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Parameter(description = "회원 미션 ID", example = "1")
            @PathVariable Long userMissionId,
            @RequestBody MissionReqDTO.UpdateMissionStatusRequest request
    ) {
        MissionResDTO.UpdateMissionStatusResponse response = missionService.updateMissionStatus(userMissionId, request);

        return ApiResponse.onSuccess(response);
    }
}
