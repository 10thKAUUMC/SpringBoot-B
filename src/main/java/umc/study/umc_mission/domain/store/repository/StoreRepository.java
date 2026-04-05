package umc.study.umc_mission.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
