package umc.study.umc_mission.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.study.umc_mission.global.common.BaseEntity;

/**
 * 음식 카테고리(FoodCategory) 엔티티 — 음식의 분류를 표현한다.
 *
 * <p>가게가 취급하는 음식 분류("한식", "일식", "중식", "양식" 등)를 저장한다.
 * 회원의 음식 취향(MemberLikes)과 연결되어, 회원이 어떤 종류의 음식을 좋아하는지를 나타낸다.</p>
 *
 * <p>이 엔티티는 단순히 이름(name) 하나만 가진 "코드 테이블"(참조 테이블)이다.
 * 코드 테이블이란, 자주 변하지 않는 분류/유형 데이터를 모아놓은 테이블을 말한다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>필드가 name 하나뿐인 단순 엔티티이지만, Enum 대신 테이블로 만든 이유:
 *       카테고리가 나중에 추가/삭제될 수 있으므로, 코드 변경 없이 DB에서 관리하는 것이 유연하다.</li>
 *   <li>MemberLikes를 통해 Member와 N:M 관계를 형성한다.</li>
 * </ul>
 */

@Entity  // JPA 엔티티 선언. DB의 food_category 테이블과 매핑된다.
@Table(name = "food_category")  // 테이블 이름을 "food_category"로 명시.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자. 외부 직접 생성 차단.
@AllArgsConstructor  // Builder 내부에서 사용하는 전체 필드 생성자.
@Builder  // 빌더 패턴 자동 생성.
public class FoodCategory extends BaseEntity {

    /* 기본 키(PK). DB가 AUTO_INCREMENT로 자동 채번. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 음식 카테고리의 이름. 예: "한식", "일식", "중식", "양식".
     * nullable = false — 카테고리 이름은 필수.
     * length = 15 — VARCHAR(15). 한글 기준 약 5글자까지 가능.
     */
    @Column(nullable = false, length = 15)
    private String name;

    /** 기본 키(PK)를 반환한다. */
    public Long getId() {
        return id;
    }

    /** 음식 카테고리의 이름을 반환한다. */
    public String getName() {
        return name;
    }
}
