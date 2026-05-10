package umc.study.umc_mission.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseSuccessCode;

/**
 * 리뷰(Review) 도메인 전용 성공 코드.
 *
 * <p>HTTP 200/201 안에서도 의미를 분기하기 위해 자체 코드를 둔다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    /** 리뷰 작성 성공 (자원 생성). */
    CREATE_REVIEW(HttpStatus.CREATED, "REVIEW2010", "성공적으로 리뷰를 작성했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
