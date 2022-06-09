package com.dokkaebi.common.exception;

import com.dokkaebi.common.ExceptionClass;
import org.springframework.http.HttpStatus;

public class UserDefindedException extends Exception {

    private static final long serialVersionUID = 4663380430591151694L;

    private ExceptionClass exceptionClass;
    private HttpStatus httpStatus;

    public UserDefindedException(ExceptionClass exceptionClass, HttpStatus httpStatus,
        String message) {
        super(exceptionClass.toString() + message);
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
    }

    public ExceptionClass getExceptionClass() {
        return exceptionClass;
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

    public String getHttpStatusType() {
        return httpStatus.getReasonPhrase();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}