package com.wasted.backend.shared.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDto {
    private final List<FieldError> fieldErrors = new ArrayList<>();

    public void addFieldError(final String fieldId, final String message) {
        final FieldError error = new FieldError(fieldId, message);
        fieldErrors.add(error);
    }

    public void addFieldErrors(final List<FieldError> fieldErrors) {
        this.fieldErrors.addAll(fieldErrors);
    }
}

