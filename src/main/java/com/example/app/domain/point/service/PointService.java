package com.example.app.domain.point.service;

import com.example.app.domain.point.dto.response.PointResponse;
import com.example.app.domain.point.repository.PointRepository;
import com.example.app.global.exception.BusinessException;
import com.example.app.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;

    public PointResponse.Total getTotalPoint(Long userId) {
        Integer total = pointRepository.sumAmountByUserId(userId);
        return PointResponse.Total.builder()
                .totalPoint(total != null ? total : 0)
                .build();
    }

    public Page<PointResponse.History> getPointHistory(Long userId, Pageable pageable) {
        return pointRepository.findByUser_UserId(userId, pageable)
                .map(PointResponse.History::from);
    }
}
