package umc.study.umc_mission.domain.point.repository.impl;

import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.point.entity.PointHistory;
import umc.study.umc_mission.domain.point.repository.PointHistoryJpaRepository;
import umc.study.umc_mission.domain.point.repository.PointHistoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * PointHistoryRepository의 JPA 구현체.
 * 내부적으로 Spring Data JPA(PointHistoryJpaRepository)를 사용하여 DB에 접근한다.
 */
@Repository
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final PointHistoryJpaRepository jpaRepository;

    public PointHistoryRepositoryImpl(PointHistoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return jpaRepository.save(pointHistory);
    }

    @Override
    public Optional<PointHistory> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<PointHistory> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(PointHistory pointHistory) {
        jpaRepository.delete(pointHistory);
    }
}
