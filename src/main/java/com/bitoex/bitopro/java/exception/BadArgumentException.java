package com.bitoex.bitopro.java.exception;

public class BadArgumentException extends ApiException {

    public BadArgumentException(String statusLine, String message) {
        super(422, statusLine, message);
    }
}
