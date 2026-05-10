package com.example.app.domain.store.dto.response;

import com.example.app.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreResponse {
    private Long storeId;
    private String name;
    private String category;
    private String address;
    private Float lat;
    private Float lng;
    private String description;

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .category(store.getCategory())
                .address(store.getAddress())
                .lat(store.getLat())
                .lng(store.getLng())
                .description(store.getDescription())
                .build();
    }
}
