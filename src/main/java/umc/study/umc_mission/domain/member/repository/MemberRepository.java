package umc.study.umc_mission.domain.member.repository;

import umc.study.umc_mission.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

/**
 * Member(회원) 도메인의 Repository 인터페이스.
 *
 * <p>이 인터페이스는 Spring Data JPA의 JpaRepository를 직접 상속하지 않고,
 * 비즈니스 로직에서 필요한 메서드만 직접 정의하는 방식을 사용한다.
 * 이렇게 하면 실제 데이터베이스 접근 방식(JPA, MyBatis, 인메모리 등)을
 * 구현체(Impl 클래스)에서 자유롭게 결정할 수 있다.</p>
 *
 * <p>이 패턴을 "포트-어댑터(Port-Adapter)" 또는 "헥사고날 아키텍처"라고 부른다.
 * 도메인 계층은 이 인터페이스(포트)에만 의존하고,
 * 인프라 계층에서 구체적인 구현(어댑터)을 제공하는 구조이다.</p>
 *
 * <p>정의된 메서드:</p>
 * <ul>
 *   <li>{@link #save(Member)} — 회원 정보를 저장하거나 수정</li>
 *   <li>{@link #findById(Long)} — 회원 ID로 단건 조회 (Optional 반환)</li>
 *   <li>{@link #findAll()} — 전체 회원을 List로 조회</li>
 *   <li>{@link #delete(Member)} — 회원 삭제</li>
 * </ul>
 */
public interface MemberRepository {

    /**
     * 회원 엔티티를 저장한다.
     * 새로운 회원이면 INSERT, 이미 존재하는 회원이면 UPDATE가 수행된다.
     *
     * @param member 저장할 회원 엔티티
     * @return 저장된 회원 엔티티 (ID가 채워진 상태)
     */
    Member save(Member member);

    /**
     * 회원 ID로 단건 조회한다.
     * Optional로 감싸서 반환하므로, 조회 결과가 없을 때 null 대신 Optional.empty()를 반환한다.
     *
     * @param id 조회할 회원의 기본 키(PK)
     * @return 해당 ID의 회원이 존재하면 Optional.of(member), 없으면 Optional.empty()
     */
    Optional<Member> findById(Long id);

    /**
     * 전체 회원 목록을 조회한다.
     *
     * @return 모든 회원 엔티티의 리스트 (데이터가 없으면 빈 리스트)
     */
    List<Member> findAll();

    /**
     * 회원을 삭제한다.
     *
     * @param member 삭제할 회원 엔티티
     */
    void delete(Member member);

    /**
     * 8주차 추가 — 이메일로 회원을 조회한다.
     *
     * <p>{@code CustomUserDetailsService.loadUserByUsername}에서 사용. 폼 로그인의 username 필드를
     * 이메일로 받기 때문에 이메일을 키로 회원을 식별한다.</p>
     */
    Optional<Member> findByEmail(String email);

    /**
     * 8주차 추가 — 이메일 중복 여부 확인.
     * 회원가입 API에서 중복 이메일이면 {@code DUPLICATE_EMAIL}로 실패시키기 위해 사용.
     */
    boolean existsByEmail(String email);
}
