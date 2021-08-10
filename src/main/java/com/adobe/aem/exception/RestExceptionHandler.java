package com.adobe.aem.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This is the exception handler class that overrides the default Spring Boot JSON response for several exceptions.
 *
 * @author  Ankith Aiyar
 * @version 1.0
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Overriding HttpRequestMethodNotSupportedException with a custom error object. Only GET is supported, everything
     * else will throw this exception.
     *
     * @param ex the exception object being handled
     * @param headers the request headers
     * @param status the response status
     * @param request the request details
     * @return ResponseEntity the error details of the request
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(String.format("Unsupported method: %s - %s", ex.getMethod(), request.getDescription(true)));
        String path = request.getDescription(false).substring(4);
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, "Unsupported method", path);
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Overriding MissingServletRequestParameterException with a custom error object when a required parameter is missing.
     *
     * @param ex the exception object being handled
     * @param headers the request headers
     * @param status the response status
     * @param request the request details
     * @return ResponseEntity the error details of the request
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(String.format("Bad request: %s - %s parameter is missing", request.getDescription(true), ex.getParameterName()));
        String path = request.getDescription(false).substring(4);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Required parameter is missing", path);
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
