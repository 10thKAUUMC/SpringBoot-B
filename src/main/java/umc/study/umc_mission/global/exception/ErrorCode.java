package umc.study.umc_mission.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 애플리케이션 전역에서 사용하는 에러 코드 정의.
 *
 * <p>각 에러 코드는 HTTP 상태, 고유 코드 문자열, 사용자용 메시지를 가진다.
 * 도메인별로 그룹화하여 관리하면, 에러 응답을 일관성 있게 내려줄 수 있다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_404", "요청한 리소스를 찾을 수 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_404", "존재하지 않는 회원입니다."),

    // Mission
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION_404", "존재하지 않는 미션입니다."),

    // Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE_404", "존재하지 않는 가게입니다."),

    // Review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_404", "존재하지 않는 리뷰입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
