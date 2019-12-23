package com.wasted.backend.shared.exception_handlers;

import com.wasted.backend.shared.exceptions.ValidationException;
import com.wasted.backend.shared.entities.ValidationErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDto handleRestValidationException(final ValidationException ex) {
        final ValidationErrorDto dto = new ValidationErrorDto();
        dto.addFieldErrors(ex.getErrors());
        return dto;
    }
}
