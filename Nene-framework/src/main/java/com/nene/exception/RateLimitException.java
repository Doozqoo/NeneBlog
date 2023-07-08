package com.nene.exception;

import org.springframework.http.HttpStatus;

public class RateLimitException extends RuntimeException {

    private final HttpStatus status;

    public RateLimitException(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}