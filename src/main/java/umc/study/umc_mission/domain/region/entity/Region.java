package umc.study.umc_mission.domain.region.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.domain.store.entity.Store;
import umc.study.umc_mission.global.common.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 지역(Region) 엔티티 — 가게가 위치한 지역 정보를 표현한다.
 *
 * <p>"서울", "부산", "대구" 등 지역 정보를 저장한다.
 * 가게(Store)는 반드시 하나의 Region에 소속되며, 지역별 가게 검색 등에 활용된다.</p>
 *
 * <p>FoodCategory와 마찬가지로, 자주 변하지 않는 참조 데이터를 관리하는 코드 테이블이다.
 * Enum 대신 테이블로 관리하여, 새 지역 추가 시 코드 변경 없이 DB INSERT만으로 해결할 수 있다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>Store가 @ManyToOne으로 Region을 참조 — 하나의 지역에 여러 가게가 속할 수 있다.</li>
 *   <li>name 필드 하나만 있는 단순 구조이지만, 필요에 따라 위도/경도 등을 확장할 수 있다.</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 region 테이블과 매핑된다.
@Table(name = "region")  // 테이블 이름을 "region"으로 명시.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자. 외부 직접 생성 차단.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder  // 빌더 패턴 자동 생성.
public class Region extends BaseEntity {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 지역의 이름. 예: "서울", "부산", "대구", "인천".
     * nullable = false — 지역 이름은 필수.
     * length = 30 — VARCHAR(30). 긴 지역명(예: "충청남도 아산시")도 수용 가능.
     */
    @Column(nullable = false, length = 30)
    private String name;

    /*
     * 6주차 추가 — 이 지역에 속한 가게 목록 (1:N).
     * 홈 화면 "지역 기준 도전 가능 미션 목록" 조회 시 region.getStores()→stores.getMissions()
     * 그래프 탐색이 가능해지지만, 실제 페이징 쿼리는 Repository에서 JPQL로 직접 작성한다.
     */
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Store> stores = new ArrayList<>();

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 지역의 이름을 반환한다. */
    public String getName() {
        return name;
    }

    /** 지역에 속한 가게 목록을 읽기 전용으로 반환한다. */
    public List<Store> getStores() {
        return Collections.unmodifiableList(stores);
    }
}
