package com.example.umc10th.domain.mission.dto;

public class MissionReqDTO {

    public record UpdateMissionStatusRequest(
            String status
    ) {
    }
}
