package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.enums.UserMissionStatus;
import com.example.umc10th.domain.mission.exception.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK, result);
    }

    @GetMapping("/{missionId}")
    public ApiResponse<MissionResDTO.MissionDetailDTO> getMissionDetail(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long missionId
    ) {
        MissionResDTO.MissionDetailDTO result =
                new MissionResDTO.MissionDetailDTO(missionId, "미션 상세 조회 테스트");

        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK, result);
    }

    @PostMapping("/{missionId}/challenge")
    public ApiResponse<String> challengeMission(
            @PathVariable Long missionId,
            @RequestParam Long userId
    ) {
        missionService.challengeMission(userId, missionId);

        return ApiResponse.onSuccess(
                GeneralSuccessSuccessCode.OK,
                "미션 시작 성공"
        );
    }

    @PostMapping("/{missionId}/complete")
    public ApiResponse<String> completeMission(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long missionId
    ) {
        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK, "미션 성공 처리 완료");
    }

    @GetMapping("/users/{userId}/missions")
    public ApiResponse<MissionResDTO.MyMissionListDTO> getMyMissions(
            @PathVariable Long userId,
            @RequestParam UserMissionStatus status,
            @RequestParam(defaultValue = "0") Integer page)
    {
        return ApiResponse.onSuccess(GeneralSuccessSuccessCode.OK,missionService.getMyMissions(userId, status, page));
    }

    @GetMapping("/home")
    public ApiResponse<MissionResDTO.HomeMissionListDTO> getHomeMissions(
            @RequestParam Long regionId,
            @RequestParam MissionStatus status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessSuccessCode.OK,
                missionService.getHomeMissions(regionId, status, page, size)
        );
    }

    @PostMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    ){
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }

    @GetMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<List<MissionResDTO.GetMission>> getMissions(
            @PathVariable Long storeId
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(storeId));
    }

    @PostMapping("/in-progress")
    public ApiResponse<MissionResDTO.MyMissionListDTO> getInProgressMissions(
            @RequestBody MissionReqDTO.InProgressMissionDTO request,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        return ApiResponse.onSuccess(
                GeneralSuccessSuccessCode.OK,
                missionService.getInProgressMissions(request.userId(), page)
        );
    }



}
