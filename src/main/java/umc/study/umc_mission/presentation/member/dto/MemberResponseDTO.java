package umc.study.umc_mission.presentation.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 회원(Member) 도메인 응답 DTO 모음.
 *
 * <p>응답 DTO를 굳이 별도로 두는 이유:</p>
 * <ol>
 *   <li><b>엔티티의 직접 노출 방지</b> — Member 엔티티에는 비밀번호 해시 등
 *       클라이언트가 알 필요 없는 내부 필드가 추가될 수 있다. 응답 DTO를 두면
 *       무엇을 노출할지 명시적으로 결정할 수 있다.</li>
 *   <li><b>API 스펙 안정성</b> — 엔티티 필드를 변경해도(예: 컬럼 추가) 응답 DTO만
 *       동결시켜두면 외부 API 계약이 깨지지 않는다.</li>
 *   <li><b>Lazy 컬렉션 직렬화 사고 방지</b> — JPA 엔티티를 직접 직렬화하면
 *       Lazy로 감싼 연관관계가 풀리며 N+1 쿼리가 터지거나 무한 순환 참조가 발생하기 쉽다.
 *       DTO는 필요한 값만 평면화해서 담으므로 안전하다.</li>
 * </ol>
 *
 * <p>구조는 {@link MemberRequestDTO}와 동일하게 외부 클래스 한 곳에 중첩 record로 모았다.</p>
 */
public class MemberResponseDTO {

    /**
     * 마이페이지 조회 응답.
     *
     * <p>워크북 예시 응답에는 {@code profileUrl}도 있지만, 현재 Member 엔티티에는 해당 필드가
     * 존재하지 않으므로 일단 제외한다 (추후 프로필 이미지 기능 추가 시 함께 도입).</p>
     *
     * <p>워크북 응답 예시 vs 현재 구현 차이:</p>
     * <ul>
     *   <li>워크북: {@code "name": "nickname012"} — name 필드명에 nickname 값이 들어감 (네이밍 모호)</li>
     *   <li>현재: {@code name}(실명)과 {@code nickname}(별명)을 분리해 의미를 명확히 함</li>
     * </ul>
     *
     * <p>record에 {@code @Builder}를 다는 이유: 컨버터에서 필드가 많은 객체를 만들 때
     * 어떤 값이 어떤 필드로 가는지 코드 가독성을 높이기 위해서. (생성자 인자 순서로 만들면
     * 필드를 추가/순서 변경할 때 사일런트 버그가 나기 쉽다.)</p>
     */
    @Builder
    @Schema(description = "마이페이지 조회 응답")
    public record MyPageResponse(

            @Schema(description = "회원 실명", example = "김아리")
            String name,

            @Schema(description = "회원 닉네임", example = "ari_kim")
            String nickname,

            @Schema(description = "이메일 주소", example = "ari@example.com")
            String email,

            @Schema(description = "전화번호 (없을 수 있음)", example = "010-1234-5678")
            String phoneNumber,

            @Schema(description = "보유 포인트", example = "2500")
            Long point

    ) {
    }
}
