package com.example.app.domain.mission.repository;

import com.example.app.domain.mission.entity.UserMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
    Page<UserMission> findByUser_UserId(Long userId, Pageable pageable);
    Page<UserMission> findByUser_UserIdAndStatus(Long userId, String status, Pageable pageable);
    boolean existsByUser_UserIdAndMission_MissionId(Long userId, Long missionId);
    Optional<UserMission> findByUser_UserIdAndMission_MissionId(Long userId, Long missionId);
}
