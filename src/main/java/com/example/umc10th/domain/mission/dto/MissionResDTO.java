package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.enums.UserMissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    public record MissionListDTO(
            Long regionId,
            String status,
            Integer page,
            Integer size
    ) {}

    public record MissionDetailDTO(
            Long missionId,
            String message
    ) {}

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MyMissionDTO {
        private Long missionId;
        private Long pointReward;
        private String storeName;
        private String description;
        private UserMissionStatus status;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class MyMissionListDTO {
        private List<MyMissionDTO> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HomeMissionDTO {
        private Long missionId;
        private String storeName;
        private String description;
        private LocalDateTime endAt;
        private Long pointReward;
        private MissionStatus status;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HomeMissionListDTO {
        private String regionName;
        private List<HomeMissionDTO> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}
