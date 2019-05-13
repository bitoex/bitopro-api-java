package com.bitoex.bitopro.java.exception;

public class UnauthorizedApiKeyException extends ApiException {

    public UnauthorizedApiKeyException(String statusLine, String message) {
        super(401, statusLine, message);
    }
}
