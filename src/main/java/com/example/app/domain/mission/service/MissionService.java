package com.example.app.domain.mission.service;

import com.example.app.domain.mission.dto.response.MissionResponse;
import com.example.app.domain.mission.entity.Mission;
import com.example.app.domain.mission.entity.UserMission;
import com.example.app.domain.mission.repository.MissionRepository;
import com.example.app.domain.mission.repository.StoreMissionRepository;
import com.example.app.domain.mission.repository.UserMissionRepository;
import com.example.app.domain.point.entity.Point;
import com.example.app.domain.point.repository.PointRepository;
import com.example.app.domain.user.entity.User;
import com.example.app.domain.user.repository.UserRepository;
import com.example.app.global.exception.BusinessException;
import com.example.app.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final StoreMissionRepository storeMissionRepository;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    public Page<MissionResponse.Info> getMissions(Pageable pageable) {
        return missionRepository.findAll(pageable).map(MissionResponse.Info::from);
    }

    public MissionResponse.Info getMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MISSION_NOT_FOUND));
        return MissionResponse.Info.from(mission);
    }

    @Transactional
    public MissionResponse.UserMissionInfo acceptMission(Long userId, Long missionId) {
        if (userMissionRepository.existsByUser_UserIdAndMission_MissionId(userId, missionId)) {
            throw new BusinessException(ErrorCode.MISSION_ALREADY_ACCEPTED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MISSION_NOT_FOUND));

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .status("IN_PROGRESS")
                .startedAt(LocalDateTime.now())
                .build();

        return MissionResponse.UserMissionInfo.from(userMissionRepository.save(userMission));
    }

    @Transactional
    public MissionResponse.UserMissionInfo completeMission(Long userId, Long userMissionId) {
        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MISSION_NOT_FOUND));

        if (!userMission.getUser().getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
        if (!"IN_PROGRESS".equals(userMission.getStatus())) {
            throw new BusinessException(ErrorCode.MISSION_NOT_IN_PROGRESS);
        }

        userMission.complete();

        // 포인트 적립
        Point point = Point.builder()
                .user(userMission.getUser())
                .amount(userMission.getMission().getRewardPoint())
                .reason("미션 완료: " + userMission.getMission().getTitle())
                .build();
        pointRepository.save(point);

        return MissionResponse.UserMissionInfo.from(userMission);
    }

    public Page<MissionResponse.UserMissionInfo> getUserMissions(Long userId, String status, Pageable pageable) {
        Page<UserMission> missions = (status != null)
                ? userMissionRepository.findByUser_UserIdAndStatus(userId, status, pageable)
                : userMissionRepository.findByUser_UserId(userId, pageable);
        return missions.map(MissionResponse.UserMissionInfo::from);
    }
}
