package umc.study.umc_mission.domain.mission.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.study.umc_mission.global.apiPayload.code.BaseErrorCode;

/**
 * 미션(Mission) 도메인 전용 에러 코드.
 *
 * <p>코드 컨벤션: {@code MISSION + HTTP상태(3) + 일련번호}.</p>
 */
@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    /** 식별자에 해당하는 미션이 존재하지 않을 때. */
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION4040", "존재하지 않는 미션입니다."),

    /** 식별자에 해당하는 지역이 존재하지 않을 때. */
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION4041", "존재하지 않는 지역입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
