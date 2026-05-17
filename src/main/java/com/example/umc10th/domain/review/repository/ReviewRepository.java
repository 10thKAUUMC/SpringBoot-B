package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            select count(r)
            from Review r
            where r.member.id = :memberId
            """)
    long countByMemberId(@Param("memberId") Long memberId);
}
