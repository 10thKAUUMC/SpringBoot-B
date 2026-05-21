package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.store.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {
    public static MissionResDTO.MyMissionDTO toMyMissionDTO(UserMission userMission) {
        return new MissionResDTO.MyMissionDTO(
                userMission.getMission().getMissionId(),
                userMission.getMission().getPointReward(),
                userMission.getMission().getStore().getName(),
                userMission.getMission().getDescription(),
                userMission.getStatus()
        );
    }

    public static MissionResDTO.MyMissionListDTO toMyMissionListDTO(Page<UserMission> userMissionPage) {
        List<MissionResDTO.MyMissionDTO> missionDTOList = userMissionPage.stream()
                .map(MissionConverter::toMyMissionDTO)
                .toList();

        return new MissionResDTO.MyMissionListDTO(
                missionDTOList,
                missionDTOList.size(),
                userMissionPage.getTotalPages(),
                userMissionPage.getTotalElements(),
                userMissionPage.isFirst(),
                userMissionPage.isLast()
                );
    }

    public static MissionResDTO.HomeMissionDTO toHomeMissionDTO(Mission mission) {
        return new MissionResDTO.HomeMissionDTO(
                mission.getMissionId(),
                mission.getStore().getName(),
                mission.getDescription(),
                mission.getEndAt(),
                mission.getPointReward(),
                mission.getStatus()
                );
    }

    public static MissionResDTO.HomeMissionListDTO toHomeMissionListDTO(
            String regionName,
            Page<Mission> missionPage
    ) {
        List<MissionResDTO.HomeMissionDTO> missionDTOList = missionPage.stream()
                .map(MissionConverter::toHomeMissionDTO)
                .toList();

        return new MissionResDTO.HomeMissionListDTO(
                regionName,
                missionDTOList,
                missionDTOList.size(),
                missionPage.getTotalPages(),
                missionPage.getTotalElements(),
                missionPage.isFirst(),
                missionPage.isLast()
                );
    }

    public static Mission toMission(
            Store store,
            MissionReqDTO.CreateMission dto
    ) {
        return Mission.builder()
                .store(store)
                .description(dto.conditional())
                .pointReward(dto.point())
                .endAt(dto.deadline())
                .status(MissionStatus.ONGOING)
                .build();
    }

    public static MissionResDTO.GetMission toGetMission(
            Mission mission
    ) {
        return new MissionResDTO.GetMission(
                mission.getMissionId(),
                mission.getPointReward(),
                mission.getDescription()
        );

    }

    }

