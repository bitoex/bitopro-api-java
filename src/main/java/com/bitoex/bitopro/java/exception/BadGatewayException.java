package com.bitoex.bitopro.java.exception;

public class BadGatewayException extends ApiException {
    public BadGatewayException(String statusLine, String message) {
        super(502, statusLine, message);
    }
}
