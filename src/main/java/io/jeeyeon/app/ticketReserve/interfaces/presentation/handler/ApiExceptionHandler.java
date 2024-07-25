package io.jeeyeon.app.ticketReserve.interfaces.presentation.handler;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseDto<?>> handleBaseException(BaseException ex) {

        String message;
        HttpStatus status;

        if (ex.getErrorType() != null) {
            status = ex.getErrorType().getHttpStatus();
            message = ex.getErrorType().getMessage();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = ex.getMessage();
        }
        log.error("error occurred : {}", message, ex);
        ResponseDto<?> error = ResponseDto.error(message);
        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ResponseDto<?>> handleDateTimeParseException(Exception e) {
        String errMessage = "Invalid datetime format. Please provide datetime in format 'yyyy-MM-dd HH:mm:ss'.";
        log.error("error occurred : {}", errMessage, e);

        ResponseDto<?> error = ResponseDto.error(errMessage);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleGeneralException(Exception e) {
        String errMessage = e.getMessage() == null ? "An unexpected error occurred" : e.getMessage();
        log.error("error occurred : {}", errMessage, e);

        ResponseDto<?> error = ResponseDto.error(errMessage);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
