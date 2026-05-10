package umc.study.umc_mission.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;

/**
 * 리뷰(Review) 도메인 전용 에러 코드.
 *
 * <p>리뷰 작성/조회에서 발생하는 비즈니스 예외만 정의한다.
 * 코드 컨벤션은 다른 도메인과 동일: {@code REVIEW + HTTP상태(3) + 일련번호(1)}.</p>
 */
@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    /** 평점 범위(1~5)를 벗어난 값을 보냈을 때. */
    INVALID_RATING(HttpStatus.BAD_REQUEST, "REVIEW4000", "별점은 1점에서 5점 사이여야 합니다."),

    /** 식별자에 해당하는 리뷰가 존재하지 않을 때. */
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW4040", "존재하지 않는 리뷰입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
