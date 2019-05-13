package com.bitoex.bitopro.java.exception;

public class ServiceUnavailableException extends ApiException {

    public ServiceUnavailableException(String statusLine, String message) {
        super(503, statusLine, message);
    }
}
