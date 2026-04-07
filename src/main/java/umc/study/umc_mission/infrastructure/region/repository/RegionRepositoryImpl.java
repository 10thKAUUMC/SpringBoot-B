package umc.study.umc_mission.infrastructure.region.repository;

import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.region.entity.Region;
import umc.study.umc_mission.domain.region.repository.RegionRepository;

import java.util.List;
import java.util.Optional;

/**
 * RegionRepository의 JPA 구현체.
 * 내부적으로 Spring Data JPA(RegionJpaRepository)를 사용하여 DB에 접근한다.
 */
@Repository
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository jpaRepository;

    public RegionRepositoryImpl(RegionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Region save(Region region) {
        return jpaRepository.save(region);
    }

    @Override
    public Optional<Region> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Region> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(Region region) {
        jpaRepository.delete(region);
    }
}
