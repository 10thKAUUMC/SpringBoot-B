package umc.study.umc_mission.infrastructure.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.domain.member.exception.MemberErrorCode;
import umc.study.umc_mission.domain.member.exception.MemberException;
import umc.study.umc_mission.domain.member.repository.MemberRepository;
import umc.study.umc_mission.domain.member.service.MemberService;
import umc.study.umc_mission.presentation.member.converter.MemberConverter;
import umc.study.umc_mission.presentation.member.dto.MemberResponseDTO;

/**
 * {@link MemberService}의 JPA 기반 구현체.
 *
 * <p>워크북에서 "Service & Repository는 다음 주차"라고 했지만, 컨트롤러를 동작시키려면
 * 최소한의 서비스 구현이 필요하므로 5주차에 골격을 잡아두고 6주차에 살을 붙이는 방식으로 진행한다.
 * 마이페이지 조회는 단순 단건 조회라 5주차 시점에서도 무리 없이 구현 가능하다.</p>
 *
 * <h3>왜 인프라 계층에 두는가?</h3>
 * <p>이 클래스는 Spring의 {@code @Service}, {@code @Transactional}이라는
 * 프레임워크 어노테이션과 결합되어 있고, 도메인 Repository를 호출(=실제 DB 접근)하기 때문이다.
 * 도메인은 "무엇을 한다"만 정의하고, 인프라는 "어떻게 한다"를 담당한다.</p>
 *
 * <h3>왜 {@code @Transactional(readOnly = true)}인가?</h3>
 * <p>마이페이지 조회는 SELECT만 수행하는 읽기 전용 트랜잭션이다.
 * {@code readOnly = true}를 붙이면 Hibernate가 변경 감지(dirty checking)와 flush를
 * 생략하므로 약간의 성능 이점을 얻고, 실수로 INSERT/UPDATE를 시도하면 즉시 실패하므로
 * "읽기만 한다"는 의도가 코드에 명시된다.</p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    /**
     * 도메인 Repository 인터페이스에 의존한다.
     * 실제 구현체(MemberRepositoryImpl)는 Spring이 주입해준다.
     * 이 클래스는 JpaRepository나 EntityManager를 직접 알 필요가 없다.
     */
    private final MemberRepository memberRepository;

    /**
     * 마이페이지 조회.
     *
     * <p>흐름:</p>
     * <ol>
     *   <li>Repository에서 회원 단건 조회</li>
     *   <li>없으면 도메인 Exception을 던짐 → 전역 어드바이스가 ApiResponse(실패)로 변환</li>
     *   <li>있으면 컨버터로 응답 DTO 생성 후 반환</li>
     * </ol>
     */
    @Override
    public MemberResponseDTO.MyPageResponse getMyPage(Long memberId) {
        // findById는 Optional<Member>를 반환하므로 .orElseThrow로 부재 처리.
        // 부재 시 던지는 예외에 도메인 ErrorCode를 동봉하면, 핸들러는 별도 분기 없이 응답을 만들 수 있다.
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.toMyPageResponse(member);
    }
}
