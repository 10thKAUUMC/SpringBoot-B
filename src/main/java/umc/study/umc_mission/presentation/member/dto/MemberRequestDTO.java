package umc.study.umc_mission.presentation.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * 회원(Member) 도메인 요청 DTO 모음.
 *
 * <p>여러 요청 DTO를 외부 클래스 한 곳에 모아두는 패턴이다. 실제 데이터 클래스는
 * 안쪽 {@code static} 중첩 record들이며, 사용 시 {@code MemberRequestDTO.MyPageRequest}
 * 형태로 호출한다.</p>
 *
 * <h3>왜 record를 쓰는가?</h3>
 * <ul>
 *   <li>모든 필드가 자동으로 {@code final} → 불변(immutable) → 한번 만들어진 요청 객체를
 *       서비스 코드가 실수로 변경할 수 없다.</li>
 *   <li>생성자, getter, equals/hashCode/toString이 자동 생성 → 보일러플레이트 제거.</li>
 *   <li>필드 순서가 곧 JSON 파싱 시 인자 순서가 되므로, 요청 본문 스키마가 코드에 명시적으로 드러난다.</li>
 * </ul>
 *
 * <h3>왜 외부 클래스 자체는 인스턴스화 못 하게 막지 않는가?</h3>
 * <p>중첩 record들이 {@code static}이므로 외부 클래스는 단순한 네임스페이스 역할이고,
 * 인스턴스를 만들 일이 없기 때문이다. 굳이 private 생성자로 막지 않아도 실수가 일어날 가능성이 없다.</p>
 */
public class MemberRequestDTO {

    /**
     * 마이페이지 조회 요청 본문.
     *
     * <p>API 명세서 상 임시로 Request Body에 회원 ID를 받는 형태로 설계했다.
     * 본래는 인증 토큰(JWT)에서 회원 ID를 추출하지만, JWT는 9주차 Spring Security에서 다루므로
     * 그 전까지 임시로 Body 파라미터로 받기로 약속한 상태이다.</p>
     *
     * <p>예시:</p>
     * <pre>{@code
     * { "id": 1 }
     * }</pre>
     *
     * @param id 조회 대상 회원의 PK
     */
    @Schema(description = "마이페이지 조회 요청 (현재는 임시로 회원 ID를 Body로 받는다 — 9주차 JWT 도입 후 제거 예정)")
    public record MyPageRequest(

            // @NotNull은 컨트롤러에서 @Valid와 함께 사용하면 자동으로 400 응답을 내려준다.
            // 추후 ValidationExceptionHandler가 추가되면 통일된 에러 응답으로 변환된다.
            @Schema(description = "조회 대상 회원의 PK", example = "1")
            @NotNull(message = "회원 ID는 필수입니다.")
            Long id

    ) {
    }
}
