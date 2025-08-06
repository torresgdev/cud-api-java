package com.project.api_crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundConflictException extends RuntimeException {
    public NotFoundConflictException(String message) {
        super(message);
    }
}
