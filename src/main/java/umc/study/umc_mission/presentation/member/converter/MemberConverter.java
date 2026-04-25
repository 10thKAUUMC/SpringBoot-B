package umc.study.umc_mission.presentation.member.converter;

import umc.study.umc_mission.domain.member.entity.Member;
import umc.study.umc_mission.presentation.member.dto.MemberResponseDTO;

/**
 * Member 엔티티 ↔ Member 응답 DTO 변환기.
 *
 * <h3>왜 별도 파일로 분리했는가?</h3>
 * <p>방법 두 가지가 있다.</p>
 * <ol>
 *   <li><b>응답 DTO 안에 정적 팩토리 메서드 두기</b> — 파일 수가 줄지만,
 *       DTO에 "엔티티를 어떻게 매핑할지"라는 또 다른 책임이 추가되어 단일 책임 원칙(SRP)에 약간 어긋난다.</li>
 *   <li><b>별도의 컨버터 파일</b> (현재 채택) — DTO는 "데이터 모양"만 담당하고
 *       컨버터는 "변환 규칙"만 담당하므로 책임 분리가 명확해진다. 파일 수가 늘지만,
 *       응답 형태가 바뀔 때 변경 영역이 컨버터로 국한되어 유지보수가 쉽다.</li>
 * </ol>
 *
 * <h3>왜 클래스가 아니라 정적 메서드 묶음인가?</h3>
 * <p>변환 로직은 상태(state)가 없는 순수 함수이므로 굳이 인스턴스화할 이유가 없다.
 * 빈으로 등록하지 않아 DI 주입 코드도 줄어든다. 다만 향후 변환에 외부 정보(예: 다국어 메시지 소스)가
 * 필요해지면 {@code @Component}로 승격해 빈으로 만들 수 있다.</p>
 *
 * <h3>왜 presentation 계층에 두는가?</h3>
 * <p>변환 결과물(MemberResponseDTO)이 presentation 계층의 산출물이기 때문이다.
 * 도메인 계층은 자신의 외부 표현 형식을 알 필요가 없다(=presentation에 의존하지 않는다).
 * 따라서 entity → DTO 매핑은 presentation 안쪽에서 수행하는 것이 의존성 방향에 맞다.</p>
 */
public final class MemberConverter {

    // 인스턴스화 방지 — 모든 메서드가 static이므로 객체를 만들 일이 없다.
    // 실수로 new MemberConverter()를 막기 위해 private 생성자 + 명시적 예외.
    private MemberConverter() {
        throw new UnsupportedOperationException("정적 유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    /**
     * Member 엔티티를 마이페이지 응답 DTO로 변환한다.
     *
     * <p>Member 엔티티에는 {@code phoneNum} 필드명을 쓰지만 응답에서는
     * 더 명확한 {@code phoneNumber}로 노출한다 (워크북 명세서 일관).</p>
     *
     * @param member 변환할 Member 엔티티 (non-null 가정 — null 처리는 호출자 책임)
     * @return 마이페이지 응답 DTO
     */
    public static MemberResponseDTO.MyPageResponse toMyPageResponse(Member member) {
        return MemberResponseDTO.MyPageResponse.builder()
                .name(member.getName())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNum())
                .point(member.getPoint())
                .build();
    }
}
