package io.jeeyeon.app.ticketReserve.domain.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    MEMBER_NOT_FOUND("멤버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SERVER_ERROR("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN("토큰 정보가 유효하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_ACTIVE_TOKEN("토큰이 활성화 되지 않았습니다.", HttpStatus.FORBIDDEN),
    EXPIRED_TOKEN("토큰이 만료되었습니다.", HttpStatus.GONE),
    ENTITY_NOT_FOUND("해당 엔터티를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_AVAILABLE_SEAT("해당 좌석은 예약 불가능합니다.", HttpStatus.CONFLICT),
    INSUFFICIENT_BALANCE("잔액이 충분하지 않습니다.", HttpStatus.FORBIDDEN),
    SEAT_ENTITY_NOT_FOUND("좌석 엔터티를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
    ;

    private final String message;
    private final HttpStatus httpStatus;

    ErrorType(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
