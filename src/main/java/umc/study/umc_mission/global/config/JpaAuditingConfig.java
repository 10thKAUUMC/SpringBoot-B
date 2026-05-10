package umc.study.umc_mission.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing(감사) 기능을 활성화하기 위한 설정 클래스.
 *
 * <p>JPA Auditing이란? — 엔티티가 생성되거나 수정될 때,
 * "생성 일시(createdAt)"와 "수정 일시(updatedAt)" 같은 필드를
 * 자동으로 채워주는 기능이다.</p>
 *
 * <p>사용 방법:</p>
 * <ol>
 *   <li>이 설정 클래스에서 {@code @EnableJpaAuditing}을 선언하여 기능을 활성화한다.</li>
 *   <li>엔티티 또는 공통 부모 클래스(BaseEntity 등)에서 {@code @CreatedDate},
 *       {@code @LastModifiedDate} 어노테이션을 필드에 붙인다.</li>
 *   <li>해당 클래스에 {@code @EntityListeners(AuditingEntityListener.class)}를 추가한다.</li>
 * </ol>
 *
 * <p>이렇게 하면 개발자가 직접 {@code new Date()}를 호출하지 않아도
 * JPA가 INSERT/UPDATE 시점에 자동으로 시간 값을 넣어준다.</p>
 */
// @Configuration — 이 클래스가 Spring 설정 클래스임을 선언한다.
// Spring이 이 클래스를 읽고, 안에 선언된 설정(@EnableJpaAuditing 등)을 적용한다.
@Configuration
// @EnableJpaAuditing — JPA Auditing 기능을 켜는 핵심 어노테이션.
// 이 어노테이션이 없으면 @CreatedDate, @LastModifiedDate가 동작하지 않는다.
@EnableJpaAuditing
public class JpaAuditingConfig {
    // 설정 활성화만 목적이므로 클래스 본문은 비어 있다.
    // 별도의 빈(Bean)이나 메서드를 선언할 필요가 없다.
}
