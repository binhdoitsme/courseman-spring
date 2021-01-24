package com.hanu.courseman.application.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR,
                reason = "The resource could not be created by the server!")
public class NotUpdatedByServerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NotUpdatedByServerException() { }
}