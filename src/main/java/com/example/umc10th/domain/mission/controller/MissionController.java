package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.enums.UserMissionStatus;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ApiResponse<MissionResDTO.MissionListDTO> getMissionList(
            @RequestHeader("Authorization") String authorization,
            @RequestParam Long regionId,
            @RequestParam String status,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        MissionResDTO.MissionListDTO result =
                new MissionResDTO.MissionListDTO(regionId, status, page, size);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

    @GetMapping("/{missionId}")
    public ApiResponse<MissionResDTO.MissionDetailDTO> getMissionDetail(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long missionId
    ) {
        MissionResDTO.MissionDetailDTO result =
                new MissionResDTO.MissionDetailDTO(missionId, "미션 상세 조회 테스트");

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

    @PostMapping("/{missionId}/challenge")
    public ApiResponse<String> challengeMission(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long missionId
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, "미션 시작 성공");
    }

    @PostMapping("/{missionId}/complete")
    public ApiResponse<String> completeMission(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long missionId
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, "미션 성공 처리 완료");
    }

    @GetMapping("/users/{userId}/missions")
    public ApiResponse<MissionResDTO.MyMissionListDTO> getMyMissions(
            @PathVariable Long userId,
            @RequestParam UserMissionStatus status,
            @RequestParam(defaultValue = "0") Integer page)
    {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,missionService.getMyMissions(userId, status, page));
    }

    @GetMapping("/home")
    public ApiResponse<MissionResDTO.HomeMissionListDTO> getHomeMissions(
            @RequestParam Long regionId,
            @RequestParam MissionStatus status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                missionService.getHomeMissions(regionId, status, page, size)
        );
    }
}
