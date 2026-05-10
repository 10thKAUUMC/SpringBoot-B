package com.example.app.domain.point.controller;

import com.example.app.domain.point.dto.response.PointResponse;
import com.example.app.domain.point.service.PointService;
import com.example.app.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Point", description = "포인트 관련 API")
@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @Operation(summary = "포인트 총합 조회")
    @GetMapping("/total")
    public ApiResponse<PointResponse.Total> getTotalPoint(@RequestParam Long userId) {
        return ApiResponse.ok(pointService.getTotalPoint(userId));
    }

    @Operation(summary = "포인트 내역 조회")
    @GetMapping("/history")
    public ApiResponse<Page<PointResponse.History>> getPointHistory(
            @RequestParam Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.ok(pointService.getPointHistory(userId, pageable));
    }
}
