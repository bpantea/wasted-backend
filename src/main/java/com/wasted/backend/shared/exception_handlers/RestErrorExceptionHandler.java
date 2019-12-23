package com.wasted.backend.shared.exception_handlers;

import com.wasted.backend.shared.entities.ErrorDto;
import com.wasted.backend.shared.exceptions.RestErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestErrorExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RestErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleValidationException(final RestErrorException ex) {
        return new ErrorDto(ex.getMessage());
    }
}
