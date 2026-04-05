package umc.study.umc_mission.domain.region.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.region.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
