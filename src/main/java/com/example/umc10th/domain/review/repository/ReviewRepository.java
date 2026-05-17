package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import java.math.BigDecimal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            select r
            from Review r
            join fetch r.store s
            join fetch r.member m
            where r.member.id = :memberId
              and (:cursorId is null or r.id < :cursorId)
            order by r.id desc
            """)
    Slice<Review> findMyReviewsByIdCursor(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
            select r
            from Review r
            join fetch r.store s
            join fetch r.member m
            where r.member.id = :memberId
              and (
                  :cursorRating is null
                  or :cursorId is null
                  or r.score < :cursorRating
                  or (r.score = :cursorRating and r.id < :cursorId)
              )
            order by r.score desc, r.id desc
            """)
    Slice<Review> findMyReviewsByRatingCursor(
            @Param("memberId") Long memberId,
            @Param("cursorRating") BigDecimal cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
            select count(r)
            from Review r
            where r.member.id = :memberId
            """)
    long countByMemberId(@Param("memberId") Long memberId);
}
