package umc.study.umc_mission.domain.review.repository.impl;

import org.springframework.stereotype.Repository;
import umc.study.umc_mission.domain.review.entity.Review;
import umc.study.umc_mission.domain.review.repository.ReviewJpaRepository;
import umc.study.umc_mission.domain.review.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

/**
 * ReviewRepository의 JPA 구현체.
 * 내부적으로 Spring Data JPA(ReviewJpaRepository)를 사용하여 DB에 접근한다.
 */
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository jpaRepository;

    public ReviewRepositoryImpl(ReviewJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Review save(Review review) {
        return jpaRepository.save(review);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Review> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void delete(Review review) {
        jpaRepository.delete(review);
    }
}
