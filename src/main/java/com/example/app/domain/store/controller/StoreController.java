package com.example.app.domain.store.controller;

import com.example.app.domain.store.dto.response.StoreResponse;
import com.example.app.domain.store.service.StoreService;
import com.example.app.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Store", description = "가게 관련 API")
@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "가게 목록 조회")
    @GetMapping
    public ApiResponse<Page<StoreResponse>> getStores(
            @RequestParam(required = false) String category,
            @PageableDefault(size = 10) Pageable pageable) {
        if (category != null) {
            return ApiResponse.ok(storeService.getStoresByCategory(category, pageable));
        }
        return ApiResponse.ok(storeService.getStores(pageable));
    }

    @Operation(summary = "가게 상세 조회")
    @GetMapping("/{storeId}")
    public ApiResponse<StoreResponse> getStore(@PathVariable Long storeId) {
        return ApiResponse.ok(storeService.getStore(storeId));
    }
}
