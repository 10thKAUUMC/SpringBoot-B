package com.example.app.domain.mission.controller;

import com.example.app.domain.mission.dto.response.MissionResponse;
import com.example.app.domain.mission.service.MissionService;
import com.example.app.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mission", description = "미션 관련 API")
@RestController
@RequestMapping("/api/v1/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @Operation(summary = "미션 목록 조회")
    @GetMapping
    public ApiResponse<Page<MissionResponse.Info>> getMissions(
            @PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.ok(missionService.getMissions(pageable));
    }

    @Operation(summary = "미션 상세 조회")
    @GetMapping("/{missionId}")
    public ApiResponse<MissionResponse.Info> getMission(@PathVariable Long missionId) {
        return ApiResponse.ok(missionService.getMission(missionId));
    }

    @Operation(summary = "미션 수락 (도전)")
    @PostMapping("/{missionId}/accept")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MissionResponse.UserMissionInfo> acceptMission(
            @PathVariable Long missionId,
            @RequestParam Long userId) {
        return ApiResponse.ok("미션을 수락했습니다.", missionService.acceptMission(userId, missionId));
    }

    @Operation(summary = "내 미션 목록 조회")
    @GetMapping("/my")
    public ApiResponse<Page<MissionResponse.UserMissionInfo>> getMyMissions(
            @RequestParam Long userId,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.ok(missionService.getUserMissions(userId, status, pageable));
    }

    @Operation(summary = "미션 완료 처리")
    @PatchMapping("/my/{userMissionId}/complete")
    public ApiResponse<MissionResponse.UserMissionInfo> completeMission(
            @PathVariable Long userMissionId,
            @RequestParam Long userId) {
        return ApiResponse.ok("미션을 완료했습니다.", missionService.completeMission(userId, userMissionId));
    }
}
