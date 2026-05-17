package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("""
    SELECT r
    FROM Review r
    JOIN FETCH r.store s
    WHERE r.user.userId = :userId
    AND (:cursorId IS NULL OR r.reviewId < :cursorId)
    ORDER BY r.reviewId DESC
""")
    List<Review> findMyReviewsOrderById(
            @Param("userId") Long userId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
    SELECT r
    FROM Review r
    JOIN FETCH r.store s
    WHERE r.user.userId = :userId
    AND (
        :cursorRating IS NULL
        OR r.rating < :cursorRating
        OR (r.rating = :cursorRating AND r.reviewId < :cursorId)
    )
    ORDER BY r.rating DESC, r.reviewId DESC
""")
    List<Review> findMyReviewsOrderByRating(
            @Param("userId") Long userId,
            @Param("cursorRating") Float cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
