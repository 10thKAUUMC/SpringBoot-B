package umc.study.umc_mission.infrastructure.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.review.entity.Review;

/**
 * Spring Data JPA가 자동으로 구현체를 생성해주는 인터페이스.
 * ReviewRepositoryImpl 내부에서만 사용된다.
 */
public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
