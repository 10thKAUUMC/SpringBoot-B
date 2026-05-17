package com.example.umc10th.domain.store.entity;

import com.example.umc10th.domain.region.entity.Region;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(nullable = false)
    private String name;

    @Column(name = "opening_time", nullable = false)
    private String openingTime;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    protected Store() {
    }

    public Store(Region region, String name, String openingTime, String description, String address) {
        this.region = region;
        this.name = name;
        this.openingTime = openingTime;
        this.description = description;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Region getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
