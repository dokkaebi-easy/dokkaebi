package com.dokkaebi.common;

public enum ExceptionClass {
    //ExceptionClass 설정
    USER("User"), PROJECT("Project"),FILE("File");

    private String exceptionClass;

    ExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    @Override
    public String toString() {
        return getExceptionClass() + " Exception. ";
    }

}

