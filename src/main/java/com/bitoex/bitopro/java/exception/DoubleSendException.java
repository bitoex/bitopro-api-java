package com.bitoex.bitopro.java.exception;

public class DoubleSendException extends ApiException {

    public DoubleSendException(String statusLine, String message) {
        super(409, statusLine, message);
    }

}
