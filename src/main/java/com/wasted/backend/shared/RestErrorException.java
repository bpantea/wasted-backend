package com.wasted.backend.shared;

public class RestErrorException extends RuntimeException {

    public RestErrorException(String message) {
        super(message);
    }
}

