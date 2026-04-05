package umc.study.umc_mission.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.mission.entity.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
