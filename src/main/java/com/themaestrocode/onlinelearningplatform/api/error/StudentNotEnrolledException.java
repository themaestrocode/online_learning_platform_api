package com.themaestrocode.onlinelearningplatform.api.error;

public class StudentNotEnrolledException extends Exception {

    public StudentNotEnrolledException() {
        super();
    }

    public StudentNotEnrolledException(String message) {
        super(message);
    }

    public StudentNotEnrolledException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotEnrolledException(Throwable cause) {
        super(cause);
    }

    protected StudentNotEnrolledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
