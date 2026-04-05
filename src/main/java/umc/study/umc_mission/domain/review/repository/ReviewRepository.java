package umc.study.umc_mission.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.study.umc_mission.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
