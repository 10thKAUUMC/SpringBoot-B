package umc.study.umc_mission.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.mission.entity.Mission;
import umc.study.umc_mission.domain.region.entity.Region;
import umc.study.umc_mission.domain.review.entity.Review;
import umc.study.umc_mission.global.common.BaseEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 가게(Store) 엔티티 — 음식점이나 상점의 정보를 표현한다.
 *
 * <p>현실 세계의 "가게"를 추상화한 것으로, 가게 이름, 소속 지역, 주소,
 * 영업 여부, 영업 시간 등의 정보를 담고 있다.</p>
 *
 * <p>미션(Mission)은 가게에 소속되고, 리뷰(Review)도 가게에 달리므로,
 * Store는 이 서비스의 핵심 엔티티 중 하나이다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>Region과 N:1 관계 — 하나의 지역에 여러 가게가 있을 수 있다.</li>
 *   <li>opened의 기본값을 false로 설정 — 가게를 등록한 직후에는 아직 영업 시작 전으로 간주.</li>
 *   <li>openTime, closeTime에 LocalTime 사용 — 날짜 없이 시간만 필요하므로 (예: 09:00, 22:00).</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 store 테이블과 매핑된다.
@Table(name = "store")  // 테이블 이름을 "store"로 명시.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자. 외부 직접 생성 차단.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder  // 빌더 패턴 자동 생성.
public class Store extends BaseEntity {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 이 가게가 속한 지역.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * - 하나의 지역(Region)에 여러 가게가 속할 수 있으므로 다대일(N:1) 관계.
     * - LAZY: 가게를 조회할 때 지역 정보를 즉시 가져오지 않고,
     *   region 필드에 접근하는 순간에야 쿼리가 실행된다 (지연 로딩).
     *
     * @JoinColumn(name = "region_id", nullable = false)
     * - store 테이블에 "region_id" 외래 키 컬럼 생성.
     * - nullable = false: 가게는 반드시 어떤 지역에 소속되어야 한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    /*
     * 가게 이름. 예: "맛있는 김치찌개".
     * nullable = false — 가게 이름은 필수.
     * length = 30 — VARCHAR(30).
     */
    @Column(nullable = false, length = 30)
    private String name;

    /*
     * 가게 유형. 예: "음식점", "카페", "베이커리" 등.
     * length = 10 — VARCHAR(10). nullable (기본값 true) — 유형이 미지정일 수 있다.
     */
    @Column(length = 10)
    private String type;

    /*
     * 가게의 상세 주소. 예: "서울시 강남구 역삼동 123-4".
     * length = 30 — VARCHAR(30). nullable — 주소가 없을 수도 있다.
     */
    @Column(length = 30)
    private String address;

    /*
     * 현재 영업 중인지 여부.
     *
     * @Builder.Default — Builder로 생성할 때 opened를 지정하지 않으면 기본값 false(영업 종료)가 들어간다.
     *   가게를 처음 등록할 때는 아직 영업을 시작하지 않은 상태로 간주하는 것이 안전하다.
     * @Column(nullable = false) — 영업 여부는 반드시 값이 있어야 한다 (null 불가).
     */
    @Builder.Default
    @Column(nullable = false)
    private Boolean opened = false;

    /*
     * 영업 시작 시간. 예: 09:00.
     * LocalTime은 시간만 표현한다 (날짜 없음).
     * null이면 영업 시간이 미등록된 상태.
     */
    private LocalTime openTime;

    /*
     * 영업 종료 시간. 예: 22:00.
     * null이면 영업 종료 시간이 미등록된 상태.
     */
    private LocalTime closeTime;

    /*
     * 6주차 추가 — 이 가게에 등록된 미션 목록 (1:N).
     * mappedBy = "store": Mission 엔티티의 store 필드가 FK 주인.
     * 가게 상세 화면에서 store.getMissions()로 즉시 조회 가능 (LAZY).
     */
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Mission> missions = new ArrayList<>();

    /*
     * 6주차 추가 — 이 가게에 달린 리뷰 목록 (1:N).
     * 가게 상세 화면 / 평점 집계에서 사용한다.
     */
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 이 가게가 속한 지역을 반환한다. */
    public Region getRegion() {
        return region;
    }

    /** 가게 이름을 반환한다. */
    public String getName() {
        return name;
    }

    /** 가게 유형을 반환한다. */
    public String getType() {
        return type;
    }

    /** 가게 주소를 반환한다. */
    public String getAddress() {
        return address;
    }

    /** 현재 영업 중인지 여부를 반환한다. true면 영업 중, false면 영업 종료. */
    public Boolean isOpened() {
        return opened;
    }

    /** 영업 시작 시간을 반환한다. null이면 미등록. */
    public LocalTime getOpenTime() {
        return openTime;
    }

    /** 영업 종료 시간을 반환한다. null이면 미등록. */
    public LocalTime getCloseTime() {
        return closeTime;
    }

    /** 가게의 미션 목록을 읽기 전용으로 반환한다. */
    public List<Mission> getMissions() {
        return Collections.unmodifiableList(missions);
    }

    /** 가게에 달린 리뷰 목록을 읽기 전용으로 반환한다. */
    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }
}
