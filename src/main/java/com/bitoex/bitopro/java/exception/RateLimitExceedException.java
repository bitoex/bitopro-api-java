package com.bitoex.bitopro.java.exception;

public class RateLimitExceedException extends ApiException {

    public RateLimitExceedException(String statusLine, String message) {
        super(429, statusLine, message);
    }
}
