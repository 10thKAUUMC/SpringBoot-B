package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import java.util.Collection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    @Query("""
            select mm
            from MemberMission mm
            join fetch mm.mission m
            join fetch m.store s
            where mm.member.id = :memberId
              and mm.status in :statuses
              and (:lastId is null or mm.id < :lastId)
            order by mm.id desc
            """)
    Slice<MemberMission> findMyMissionsByStatuses(
            @Param("memberId") Long memberId,
            @Param("statuses") Collection<MissionStatus> statuses,
            @Param("lastId") Long lastId,
            Pageable pageable
    );

    @Query("""
            select count(mm)
            from MemberMission mm
            where mm.member.id = :memberId
              and mm.status = :status
            """)
    long countByMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("status") MissionStatus status
    );

    @Query("""
            select count(mm)
            from MemberMission mm
            where mm.member.id = :memberId
            """)
    long countByMemberId(@Param("memberId") Long memberId);
}
