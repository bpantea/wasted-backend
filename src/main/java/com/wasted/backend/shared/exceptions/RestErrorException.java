package com.wasted.backend.shared.exceptions;

public class RestErrorException extends RuntimeException {

    public RestErrorException(String message) {
        super(message);
    }
}

