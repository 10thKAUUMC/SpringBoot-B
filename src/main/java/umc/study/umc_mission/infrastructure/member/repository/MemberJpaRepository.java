package umc.study.umc_mission.infrastructure.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.member.entity.Member;

/**
 * Spring Data JPA가 자동으로 구현체를 생성해주는 인터페이스.
 * MemberRepositoryImpl 내부에서만 사용된다.
 */
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
