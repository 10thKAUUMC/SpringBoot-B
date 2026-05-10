package com.example.app.domain.store.service;

import com.example.app.domain.store.dto.response.StoreResponse;
import com.example.app.domain.store.entity.Store;
import com.example.app.domain.store.repository.StoreRepository;
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
public class StoreService {

    private final StoreRepository storeRepository;

    public Page<StoreResponse> getStores(Pageable pageable) {
        return storeRepository.findAll(pageable).map(StoreResponse::from);
    }

    public Page<StoreResponse> getStoresByCategory(String category, Pageable pageable) {
        return storeRepository.findByCategory(category, pageable).map(StoreResponse::from);
    }

    public StoreResponse getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return StoreResponse.from(store);
    }

    public Store getStoreEntity(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
    }
}
