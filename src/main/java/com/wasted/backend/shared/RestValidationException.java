package com.wasted.backend.shared;

import java.util.List;

public class RestValidationException extends RuntimeException {
    private final List<FieldError> errors;

    public RestValidationException(List<FieldError> errors) {
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }
}
