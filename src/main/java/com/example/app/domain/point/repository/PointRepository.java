package com.example.app.domain.point.repository;

import com.example.app.domain.point.entity.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<Point, Long> {
    Page<Point> findByUser_UserId(Long userId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Point p WHERE p.user.userId = :userId")
    Integer sumAmountByUserId(Long userId);
}
