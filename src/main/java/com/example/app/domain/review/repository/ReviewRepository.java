package com.example.app.domain.review.repository;

import com.example.app.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByStore_StoreId(Long storeId, Pageable pageable);
    Page<Review> findByUser_UserId(Long userId, Pageable pageable);
    boolean existsByUser_UserIdAndStore_StoreId(Long userId, Long storeId);
}
