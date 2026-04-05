package umc.study.umc_mission.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.global.common.BaseEntity;

import java.time.LocalTime;

/**
 * 가게(Store) 엔티티.
 * 가게 정보(이름, 지역, 영업시간 등)를 저장한다.
 */
@Entity
@Table(name = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 10)
    private String type;

    @Column(length = 30)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isOpened = false;

    private LocalTime openTime;

    private LocalTime closeTime;

    public Long getId() {
        return id;
    }

    public Region getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getIsOpened() {
        return isOpened;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }
}
