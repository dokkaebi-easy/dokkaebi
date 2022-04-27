package com.ssafy.dockerby.common.exception;

import com.ssafy.dockerby.dto.error.ErrorResponseDto;
import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//controller에서 사용되는 핸들러
@RestControllerAdvice
@Slf4j
public class UserDefindedExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> ExceptionHandler(Exception e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        // e.printStackTrace 저장 객체
        StringWriter sw = printStackTraceMapper(e);

        log.error("Error stackTrace: {}", e.getStackTrace());
        log.error("Error class : {}", e.getClass().getSimpleName());
        log.error("Error cause : {}", e.getCause());
        log.error(sw.toString());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.from(e);

        return new ResponseEntity<>(errorResponseDto, responseHeaders, httpStatus);
    }

    @ExceptionHandler(value = UserDefindedException.class)
    public ResponseEntity<ErrorResponseDto> ExceptionHandler(UserDefindedException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        StringWriter sw = printStackTraceMapper(e);

        log.error("Error stackTrace: {}", e.getStackTrace());
        log.error("Error class : {}", e.getClass().getSimpleName());
        log.error(sw.toString());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.from(e);

        return new ResponseEntity<>(errorResponseDto, responseHeaders, e.getHttpStatus());
    }


    private StringWriter printStackTraceMapper(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));

        return sw;
    }

}