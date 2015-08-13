package com.wryday.whatwhere.rest;

public class Meta {

    private int mCode;
    private ErrorType mErrorType;

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    public ErrorType getErrorType() {
        return mErrorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.mErrorType = errorType;
    }
}
