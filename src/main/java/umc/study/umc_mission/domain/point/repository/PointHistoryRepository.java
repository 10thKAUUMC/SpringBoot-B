package umc.study.umc_mission.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.point.entity.PointHistory;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}
