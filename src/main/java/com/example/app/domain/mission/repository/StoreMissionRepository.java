package com.example.app.domain.mission.repository;

import com.example.app.domain.mission.entity.StoreMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMissionRepository extends JpaRepository<StoreMission, Long> {
    Page<StoreMission> findByStore_StoreId(Long storeId, Pageable pageable);
}
