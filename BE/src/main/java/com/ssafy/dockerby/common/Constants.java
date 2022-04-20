package com.ssafy.dockerby.common;

public class Constants {

    public enum ExceptionClass {

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

}