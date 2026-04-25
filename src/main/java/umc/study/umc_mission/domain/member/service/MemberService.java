package umc.study.umc_mission.domain.member.service;

import umc.study.umc_mission.presentation.member.dto.MemberResponseDTO;

/**
 * Member(회원) 도메인 서비스 인터페이스.
 *
 * <p>비즈니스 로직(회원 조회, 가입, 수정 등)을 정의하는 포트(port)이다.
 * 구현체는 {@code infrastructure/member/service/MemberServiceImpl}에 위치하며,
 * Spring 빈으로 등록되어 트랜잭션 처리와 Repository 호출을 담당한다.</p>
 *
 * <h3>왜 인터페이스를 도메인에 두고 구현체를 인프라에 두는가?</h3>
 * <p>Repository 패턴과 동일한 이유 — 도메인 계층이 Spring/JPA 같은 인프라 기술에
 * 직접 의존하지 않도록 한다. 컨트롤러는 이 인터페이스만 보고 호출하므로,
 * 추후 구현체를 다른 기술(예: 외부 API 호출, 캐시 우선 조회)로 바꿔도 컨트롤러 수정이 필요 없다.</p>
 *
 * <h3>5주차 시점의 상태</h3>
 * <p>워크북에서 "Service & Repository는 다음 주차에서"라고 했으므로,
 * 5주차에는 인터페이스 + 최소 구현(스텁)만 둔다. 6주차 JPA 학습 후에 실제 동작을 채운다.</p>
 */
public interface MemberService {

    /**
     * 마이페이지 정보를 조회한다.
     *
     * <p>회원 ID를 받아 해당 회원의 공개 가능한 정보(이름, 닉네임, 이메일, 전화번호, 포인트)를 반환한다.</p>
     *
     * @param memberId 조회 대상 회원의 PK
     * @return 마이페이지 응답 DTO
     * @throws umc.study.umc_mission.domain.member.exception.MemberException
     *         해당 ID의 회원이 존재하지 않으면
     *         ({@link umc.study.umc_mission.domain.member.exception.MemberErrorCode#MEMBER_NOT_FOUND})
     */
    MemberResponseDTO.MyPageResponse getMyPage(Long memberId);
}
