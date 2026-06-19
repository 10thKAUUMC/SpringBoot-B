package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.UserMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.enums.UserMissionStatus;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.UserMissionRepository;
import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.region.repository.RegionRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.exception.StoreErrorCode;
import com.example.umc10th.domain.store.exception.StoreException;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.domain.user.entity.User;
import com.example.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final RegionRepository regionRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public Void createMission(
            Long storeId,
            MissionReqDTO.CreateMission dto
    ) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        Mission mission = MissionConverter.toMission(store, dto);

        missionRepository.save(mission);

        return null;
    }

    public List<MissionResDTO.GetMission> getMissions(
            Long storeId
    ) {
        List<Mission> missionList = missionRepository.findAllByStore_StoreId(storeId);

        return missionList.stream()
                .map(MissionConverter::toGetMission)
                .toList();
    }

    public MissionResDTO.MyMissionListDTO getInProgressMissions(Long userId, Integer page) {
        Page<UserMission> userMissionPage = userMissionRepository.findMyMissions(
                userId,
                UserMissionStatus.CHALLENGING,
                PageRequest.of(page, 10)
        );

        return MissionConverter.toMyMissionListDTO(userMissionPage);
    }

    @Transactional
    public void challengeMission(Long userId, Long missionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("미션을 찾을 수 없습니다."));

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .status(UserMissionStatus.CHALLENGING)
                .startedAt(LocalDateTime.now())
                .build();

        userMissionRepository.save(userMission);
    }
}
