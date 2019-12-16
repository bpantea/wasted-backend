package com.wasted.backend.shared;

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

    public synchronized void rejectIfHasErrors() {
        if (hasErrors()) {
            throw new RestValidationException(getErrors());
        }
    }
}

