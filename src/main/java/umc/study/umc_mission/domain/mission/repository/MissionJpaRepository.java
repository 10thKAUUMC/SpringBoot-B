package umc.study.umc_mission.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.mission.entity.Mission;

/**
 * Spring Data JPA가 자동으로 구현체를 생성해주는 인터페이스.
 * MissionRepositoryImpl 내부에서만 사용된다.
 */
public interface MissionJpaRepository extends JpaRepository<Mission, Long> {
}
