package com.example.app.domain.mission.dto.response;

import com.example.app.domain.mission.entity.Mission;
import com.example.app.domain.mission.entity.UserMission;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class MissionResponse {

    @Getter
    @Builder
    public static class Info {
        private Long missionId;
        private String title;
        private String description;
        private Integer rewardPoint;
        private String missionType;
        private LocalDateTime createdAt;

        public static Info from(Mission mission) {
            return Info.builder()
                    .missionId(mission.getMissionId())
                    .title(mission.getTitle())
                    .description(mission.getDescription())
                    .rewardPoint(mission.getRewardPoint())
                    .missionType(mission.getMissionType())
                    .createdAt(mission.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class UserMissionInfo {
        private Long userMissionId;
        private Long missionId;
        private String title;
        private String status;
        private LocalDateTime startedAt;
        private LocalDateTime completedAt;

        public static UserMissionInfo from(UserMission userMission) {
            return UserMissionInfo.builder()
                    .userMissionId(userMission.getUserMissionId())
                    .missionId(userMission.getMission().getMissionId())
                    .title(userMission.getMission().getTitle())
                    .status(userMission.getStatus())
                    .startedAt(userMission.getStartedAt())
                    .completedAt(userMission.getCompletedAt())
                    .build();
        }
    }
}
