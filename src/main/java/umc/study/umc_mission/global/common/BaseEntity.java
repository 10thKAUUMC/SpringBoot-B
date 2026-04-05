package umc.study.umc_mission.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 모든 Entity가 공통으로 상속하는 베이스 클래스.
 * createdAt(생성 시간), updatedAt(수정 시간)을 자동으로 관리한다.
 *
 * <p>현실 세계에서 거의 모든 데이터는 "언제 만들어졌는지", "언제 마지막으로 수정됐는지"를 추적해야 한다.
 * 이 두 컬럼을 모든 엔티티마다 반복 선언하면 코드 중복이 심해지므로,
 * 공통 부모 클래스(BaseEntity)에 한 번만 정의하고 상속(extends)으로 재사용한다.</p>
 *
 * <p>설계 포인트:</p>
 * <ul>
 *   <li>@MappedSuperclass — 이 클래스 자체는 테이블이 생성되지 않고,
 *                           상속받는 Entity의 테이블에 컬럼이 추가됨.</li>
 *   <li>@EntityListeners(AuditingEntityListener.class) — JPA Auditing 기능을 활성화하여
 *       엔티티가 저장/수정될 때 createdAt, updatedAt을 자동으로 채워준다.</li>
 *   <li>abstract class — 직접 인스턴스를 생성할 수 없도록 추상 클래스로 선언.
 *       반드시 자식 엔티티를 통해서만 사용된다.</li>
 * </ul>
 */

/*
 * @MappedSuperclass
 * - 이 어노테이션이 붙은 클래스는 JPA 엔티티가 아니라 "매핑 정보만 제공하는 부모 클래스"이다.
 * - DB에 base_entity 테이블이 생기는 것이 아니라, 자식 엔티티(Member, Store 등)의 테이블에
 *   createdAt, updatedAt 컬럼이 자동으로 포함된다.
 * - 비유하자면: "설계도의 공통 부분"을 한 장에 모아놓고, 각 건물 설계도에서 참조하는 것.
 */
@MappedSuperclass

/*
 * @EntityListeners(AuditingEntityListener.class)
 * - JPA의 "이벤트 리스너"를 등록하는 어노테이션이다.
 * - AuditingEntityListener는 Spring Data JPA가 제공하는 리스너로,
 *   엔티티가 처음 저장(persist)될 때 @CreatedDate 필드에 현재 시간을 넣어주고,
 *   수정(update)될 때 @LastModifiedDate 필드에 현재 시간을 넣어준다.
 * - 이 기능을 사용하려면 메인 Application 클래스에 @EnableJpaAuditing이 있어야 한다.
 */
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /*
     * @CreatedDate
     * - 엔티티가 처음 DB에 저장(INSERT)되는 시점의 시간을 자동으로 기록한다.
     * - 개발자가 직접 setter를 호출할 필요 없이, JPA가 알아서 현재 시간을 넣어준다.
     *
     * @Column(updatable = false)
     * - 한 번 저장된 생성 시간은 절대 변경되면 안 되므로, UPDATE SQL에서 이 컬럼을 제외시킨다.
     * - 만약 이 설정이 없으면, 엔티티를 수정할 때 createdAt이 덮어써질 위험이 있다.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /*
     * @LastModifiedDate
     * - 엔티티가 수정(UPDATE)될 때마다 자동으로 현재 시간으로 갱신된다.
     * - 예: 회원이 닉네임을 변경하면, 해당 회원의 updatedAt이 자동으로 현재 시각으로 바뀐다.
     * - updatable = false가 없으므로, 매번 UPDATE 시 값이 바뀐다 (의도된 동작).
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /*
     * createdAt의 Getter — 생성 시간을 외부에서 읽을 수 있도록 제공한다.
     * 예: API 응답에서 "이 데이터가 언제 만들어졌는지" 보여줄 때 사용.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /*
     * updatedAt의 Getter — 마지막 수정 시간을 외부에서 읽을 수 있도록 제공한다.
     * 예: "최근 수정일" 표시에 사용.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
