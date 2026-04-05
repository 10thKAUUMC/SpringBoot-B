package umc.study.umc_mission.domain.store.repository.impl;

import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.domain.store.repository.StoreJpaRepository;
import umc.study.umc_mission.domain.store.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

/**
 * StoreRepository의 JPA 구현체.
 * 내부적으로 Spring Data JPA(StoreJpaRepository)를 사용하여 DB에 접근한다.
 */
@Repository
public class StoreRepositoryImpl implements StoreRepository {

    private final StoreJpaRepository jpaRepository;

    public StoreRepositoryImpl(StoreJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Store save(Store store) {
        return jpaRepository.save(store);
    }

    @Override
    public Optional<Store> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Store> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(Store store) {
        jpaRepository.delete(store);
    }
}
