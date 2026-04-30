package com.example.period.tracker.domain.exception;

public abstract class TrackerException extends RuntimeException {
    private final String errorCode;

    protected TrackerException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return errorCode;
    }
}

