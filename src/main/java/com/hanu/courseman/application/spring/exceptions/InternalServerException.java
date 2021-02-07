package com.hanu.courseman.application.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR,
                reason = "The server encountered an error, please try again!")
public class InternalServerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InternalServerException() { }
}
