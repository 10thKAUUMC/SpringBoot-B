package com.example.umc10th.domain.mission.dto;

import java.time.LocalDate;
import java.util.List;

public class MissionResDTO {

    public record MissionListResponse(
            List<MissionSummaryResponse> missions,
            Boolean hasNext
    ) {
    }

    public record MissionSummaryResponse(
            Long userMissionId,
            Long missionId,
            Long storeId,
            String storeName,
            String content,
            LocalDate deadline,
            Integer point,
            String status
    ) {
    }

    public record UpdateMissionStatusResponse(
            Long userMissionId,
            String status
    ) {
    }
}
