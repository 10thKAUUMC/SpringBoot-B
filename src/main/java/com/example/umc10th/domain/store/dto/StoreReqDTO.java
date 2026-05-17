package com.example.umc10th.domain.store.dto;

import com.example.umc10th.domain.region.entity.Region;
import jakarta.validation.constraints.NotBlank;

public class StoreReqDTO {
    public record CreateStoreDTO (
            @NotBlank
            String name,
            String address,
            String introduction,
            String imageURL,
            Long regionId
    ) {}

}
