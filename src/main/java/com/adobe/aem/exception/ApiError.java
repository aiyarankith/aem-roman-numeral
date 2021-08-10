package com.adobe.aem.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * This is the error object to be returned in request exceptions.
 *
 * @author  Ankith Aiyar
 * @version 1.0
 */
public class ApiError {

    private final String timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ApiError(HttpStatus httpStatus, String message, String path) {
        timestamp = Instant.now().toString();
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

}
