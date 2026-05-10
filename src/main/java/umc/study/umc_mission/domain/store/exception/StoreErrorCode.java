package umc.study.umc_mission.domain.store.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;

/**
 * 가게(Store) 도메인 전용 에러 코드.
 *
 * <p>Member 도메인과 동일한 컨벤션: {@code STORE + HTTP상태(3) + 일련번호}.</p>
 */
@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

    /** 식별자에 해당하는 가게가 존재하지 않을 때. */
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE4040", "존재하지 않는 가게입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
