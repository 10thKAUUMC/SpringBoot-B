package com.example.app.domain.store.repository;

import com.example.app.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findByCategory(String category, Pageable pageable);
    Page<Store> findByNameContaining(String name, Pageable pageable);
}
