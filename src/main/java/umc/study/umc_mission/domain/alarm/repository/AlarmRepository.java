package umc.study.umc_mission.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.alarm.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
