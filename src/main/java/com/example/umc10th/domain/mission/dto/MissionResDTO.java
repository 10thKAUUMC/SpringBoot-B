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
    ){}

    public record MissionDetailDTO(
            Long missionId,
            String message
    ){}

    public record MyMissionDTO(
        Long missionId,
        Long pointReward,
        String storeName,
        String description,
        UserMissionStatus status
    ){}

    public record MyMissionListDTO(
        List<MyMissionDTO> missionList,
        Integer listSize,
        Integer totalPage,
        Long totalElements,
        Boolean isFirst,
        Boolean isLast
    ){}

    public record HomeMissionDTO(
        Long missionId,
        String storeName,
        String description,
        LocalDateTime endAt,
        Long pointReward,
        MissionStatus status
        ){}

    public record HomeMissionListDTO(
        String regionName,
        List<HomeMissionDTO> missionList,
        Integer listSize,
        Integer totalPage,
        Long totalElements,
        Boolean isFirst,
        Boolean isLast
    ){}

    public record GetMission(
            Long missionId,
            Long point,
            String conditional
    ){}
}
