package com.wasted.backend.shared.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDto {
    private final List<FieldError> fieldErrors = new ArrayList<>();

    public void addFieldErrors(final List<FieldError> fieldErrors) {
        this.fieldErrors.addAll(fieldErrors);
    }
}

