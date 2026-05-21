package com.example.umc10th.domain.store.entity;

import com.example.umc10th.domain.region.entity.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store")

public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "address" , nullable = false)
    private String address;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id" , nullable = false)
    private Region region;
}
