package umc.study.umc_mission.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
