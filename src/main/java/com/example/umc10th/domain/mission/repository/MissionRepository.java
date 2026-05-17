package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("""
            select m
            from Mission m
            join fetch m.store s
            join fetch s.region r
            where r.name = :region
              and m.deletedAt is null
              and (:lastId is null or m.id < :lastId)
              and not exists (
                  select mm.id
                  from MemberMission mm
                  where mm.member = :member
                    and mm.mission = m
              )
            order by m.id desc
            """)
    Slice<Mission> findAvailableMissionsByRegion(
            @Param("member") Member member,
            @Param("region") String region,
            @Param("lastId") Long lastId,
            Pageable pageable
    );
}
