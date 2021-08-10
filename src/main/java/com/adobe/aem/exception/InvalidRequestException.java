package com.adobe.aem.exception;

/**
 * This is the exception thrown when an invalid request comes in.
 *
 * @author  Ankith Aiyar
 * @version 1.0
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

}
