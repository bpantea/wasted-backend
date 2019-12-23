package com.wasted.backend.shared.entities;

import com.wasted.backend.shared.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private List<FieldError> fieldErrors;

    public ValidationResult() {
        this.fieldErrors = new ArrayList<>();
    }

    public boolean hasErrors() {
        return !this.fieldErrors.isEmpty();
    }

    public void rejectValue(String field, String message) {
        this.fieldErrors.add(new FieldError(field, message));
    }

    public List<FieldError> getErrors() {
        return this.fieldErrors;
    }

    public void rejectIfHasErrors() {
        if (hasErrors()) {
            throw new ValidationException(getErrors());
        }
    }
}

