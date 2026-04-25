package umc.study.umc_mission.global.apiPayload.code.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseSuccessCode;

/**
 * 도메인에 종속되지 않는 공통(글로벌) 성공 코드.
 *
 * <p>특정 도메인에 매핑하기 애매한 일반적인 성공 응답을 정의한다.
 * 예를 들어 단순 헬스체크 응답이나, 별다른 분기가 필요 없는 단순 생성/조회 결과 등.</p>
 *
 * <p>도메인 고유 성공 코드는 각 도메인 패키지의 별도 enum에 둔다
 * (예: {@code domain.member.exception.MemberSuccessCode}). 모두 {@link BaseSuccessCode}
 * 를 구현하므로 컨트롤러가 통일된 방식으로 {@code ApiResponse.onSuccess(code, result)}에 넘길 수 있다.</p>
 *
 * <p>코드 문자열 컨벤션: {@code COMMON} + HTTP 상태 코드(3자리) + 일련번호.</p>
 */
@Getter
@RequiredArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK, "COMMON2000", "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED, "COMMON2010", "리소스가 성공적으로 생성되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON2040", "요청은 성공했지만 응답 본문이 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
