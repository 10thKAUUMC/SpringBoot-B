package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.enums.UserMissionStatus;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final RegionRepository regionRepository;

    public MissionResDTO.MyMissionListDTO getMyMissions(Long userId, UserMissionStatus status, Integer page) {
        Page<UserMission> userMissionPage = userMissionRepository.findMyMissions(
                userId,
                status,
                PageRequest.of(page, 10)
        );

        return MissionConverter.toMyMissionListDTO(userMissionPage);
    }

    public MissionResDTO.HomeMissionListDTO getHomeMissions(
            Long regionId,
            MissionStatus status,
            Integer page,
            Integer size
    ) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("해당 지역이 없습니다."));

        Page<Mission> missionPage = missionRepository.findHomeMissions(
                regionId,
                status,
                PageRequest.of(page, size)
        );

        return MissionConverter.toHomeMissionListDTO(region.getName(), missionPage);
    }
}
