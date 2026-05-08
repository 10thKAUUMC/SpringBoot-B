package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {
    public static MissionResDTO.MyMissionDTO toMyMissionDTO(UserMission userMission) {
        return MissionResDTO.MyMissionDTO.builder()
                .missionId(userMission.getMission().getMissionId())
                .pointReward(userMission.getMission().getPointReward())
                .storeName(userMission.getMission().getStore().getName())
                .description(userMission.getMission().getDescription())
                .status(userMission.getStatus())
                .build();
    }

    public static MissionResDTO.MyMissionListDTO toMyMissionListDTO(Page<UserMission> userMissionPage) {
        List<MissionResDTO.MyMissionDTO> missionDTOList = userMissionPage.stream()
                .map(MissionConverter::toMyMissionDTO)
                .toList();

        return MissionResDTO.MyMissionListDTO.builder()
                .missionList(missionDTOList)
                .listSize(missionDTOList.size())
                .totalPage(userMissionPage.getTotalPages())
                .totalElements(userMissionPage.getTotalElements())
                .isFirst(userMissionPage.isFirst())
                .isLast(userMissionPage.isLast())
                .build();
    }

    public static MissionResDTO.HomeMissionDTO toHomeMissionDTO(Mission mission) {
        return MissionResDTO.HomeMissionDTO.builder()
                .missionId(mission.getMissionId())
                .storeName(mission.getStore().getName())
                .description(mission.getDescription())
                .endAt(mission.getEndAt())
                .pointReward(mission.getPointReward())
                .status(mission.getStatus())
                .build();
    }

    public static MissionResDTO.HomeMissionListDTO toHomeMissionListDTO(
            String regionName,
            Page<Mission> missionPage
    ) {
        List<MissionResDTO.HomeMissionDTO> missionDTOList = missionPage.stream()
                .map(MissionConverter::toHomeMissionDTO)
                .toList();

        return MissionResDTO.HomeMissionListDTO.builder()
                .regionName(regionName)
                .missionList(missionDTOList)
                .listSize(missionDTOList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }
}
