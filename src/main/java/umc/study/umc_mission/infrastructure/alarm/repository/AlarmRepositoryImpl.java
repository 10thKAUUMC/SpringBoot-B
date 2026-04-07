package umc.study.umc_mission.infrastructure.alarm.repository;

import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.alarm.entity.Alarm;
import umc.study.umc_mission.domain.alarm.repository.AlarmRepository;

import java.util.List;
import java.util.Optional;

/**
 * AlarmRepository의 JPA 구현체.
 * 내부적으로 Spring Data JPA(AlarmJpaRepository)를 사용하여 DB에 접근한다.
 */
@Repository
public class AlarmRepositoryImpl implements AlarmRepository {

    private final AlarmJpaRepository jpaRepository;

    public AlarmRepositoryImpl(AlarmJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Alarm save(Alarm alarm) {
        return jpaRepository.save(alarm);
    }

    @Override
    public Optional<Alarm> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Alarm> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(Alarm alarm) {
        jpaRepository.delete(alarm);
    }
}
