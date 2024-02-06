package com.themaestrocode.onlinelearningplatform.api.error;

public class StudentAlreadyEnrolledException extends Exception {

    public StudentAlreadyEnrolledException() {
        super();
    }

    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }

    public StudentAlreadyEnrolledException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentAlreadyEnrolledException(Throwable cause) {
        super(cause);
    }

    protected StudentAlreadyEnrolledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
