package umc.study.umc_mission.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseSuccessCode;

/**
 * 회원(Member) 도메인 전용 성공 코드.
 *
 * <p>HTTP 200/201 한 가지 안에서도 "마이페이지 조회 성공"과 "회원가입 성공"을 자체 코드로
 * 구분해두면, 프론트엔드 측의 토스트 메시지 처리나 i18n 등에 도움이 된다.</p>
 *
 * <p>코드 컨벤션: {@code MEMBER} + HTTP 상태 코드(3자리) + 일련번호.</p>
 */
@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    /** 마이페이지(자기 정보 조회) 성공. */
    GET_MY_PAGE(HttpStatus.OK, "MEMBER2000", "성공적으로 유저를 조회했습니다."),

    /** 회원가입 성공. */
    SIGN_UP(HttpStatus.CREATED, "MEMBER2010", "성공적으로 회원가입했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
