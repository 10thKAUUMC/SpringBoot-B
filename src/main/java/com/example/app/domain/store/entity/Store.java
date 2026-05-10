package com.example.app.domain.store.entity;

import com.example.app.domain.mission.entity.StoreMission;
import com.example.app.domain.review.entity.Review;
import com.example.app.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 100)
    private String category;

    @Column(length = 255)
    private String address;

    private Float lat;
    private Float lng;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<StoreMission> storeMissions = new ArrayList<>();

    public void update(String name, String category, String address, Float lat, Float lng, String description) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
    }
}
