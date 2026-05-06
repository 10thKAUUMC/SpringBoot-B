package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import java.util.List;
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
public class MissionController {

    @GetMapping
    public ApiResponse<MissionResDTO.MissionListResponse> getMissions(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam String region,
            @RequestParam String status,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        MissionResDTO.MissionListResponse response = new MissionResDTO.MissionListResponse(
                List.of(),
                false
        );

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{userMissionId}")
    public ApiResponse<MissionResDTO.UpdateMissionStatusResponse> updateMissionStatus(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long userMissionId,
            @RequestBody MissionReqDTO.UpdateMissionStatusRequest request
    ) {
        MissionResDTO.UpdateMissionStatusResponse response = new MissionResDTO.UpdateMissionStatusResponse(
                userMissionId,
                request.status()
        );

        return ApiResponse.onSuccess(response);
    }
}
