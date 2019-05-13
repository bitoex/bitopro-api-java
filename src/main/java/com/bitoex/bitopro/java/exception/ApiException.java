package com.bitoex.bitopro.java.exception;

public class ApiException extends RuntimeException {

    private final int code;

    public ApiException(int code, String statusLine, String message) {
        super(statusLine + "(" + code + "): " + message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
