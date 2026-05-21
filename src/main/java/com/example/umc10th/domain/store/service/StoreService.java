package com.example.umc10th.domain.store.service;

import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.region.repository.RegionRepository;
import com.example.umc10th.domain.store.converter.StoreConverter;
import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.dto.StoreResDTO;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class StoreService {
    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;

    @Transactional
    public StoreResDTO.CreateStoreResultDTO createStore(
            StoreReqDTO.CreateStoreDTO dto
    ) {
        Region region = regionRepository.findById(dto.regionId())
                .orElseThrow(() -> new RuntimeException("해당 지역이 없습니다."));

        Store store = StoreConverter.toStore(region, dto);

        Store savedStore = storeRepository.save(store);

        return StoreConverter.toCreateStoreResultDTO(savedStore);
    }
}
