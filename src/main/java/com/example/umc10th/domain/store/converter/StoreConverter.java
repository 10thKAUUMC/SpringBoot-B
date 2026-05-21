package com.example.umc10th.domain.store.converter;

import com.example.umc10th.domain.region.entity.Region;
import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.dto.StoreResDTO;
import com.example.umc10th.domain.store.entity.Store;

public class StoreConverter {
    public static Store toStore(
            Region region,
            StoreReqDTO.CreateStoreDTO dto
    ) {
        return Store.builder()
                .name(dto.name())
                .address(dto.address())
                .introduction(dto.introduction())
                .imageUrl(dto.imageURL())
                .region(region)
                .build();
    }

    public static StoreResDTO.CreateStoreResultDTO toCreateStoreResultDTO(
            Store store
    ) {
        return new StoreResDTO.CreateStoreResultDTO(
                store.getStoreId()
        );
    }
}
