package com.adobe.aem.util;

import com.adobe.aem.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * This is the validator for all incoming requests. If any checks fail, an InvalidRequestException is thrown.
 *
 * @author  Ankith Aiyar
 * @version 1.0
 */
@Slf4j
public class RequestValidator {

    /**
     * Validate request value.
     *
     * @param request the request being checked
     * @param min the minimum number accepted
     * @param max the maximum number accepted
     * @throws InvalidRequestException if request did not pass check
     * @return void
     */
    public static void validate(String request, int min, int max) {
        // check for null, empty, or only spaces
        if (StringUtils.isBlank(request)) {
            log.warn(String.format("Query \'%s\' is blank", request));
            throw new InvalidRequestException("Query must contain a value");
        }
        // check for valid integer
        if (!isInteger(request)) {
            log.warn(String.format("Query \'%s\' is not an integer", request));
            throw new InvalidRequestException("Query must be an integer");
        }
        // check that number is within range
        if (!isInRange(min, max, Integer.parseInt(request))) {
            log.warn(String.format("Query \'%s\' is not within range of %d-%d", request, min, max));
            throw new InvalidRequestException(String.format("Query must be within range of %d-%d", min, max));
        }
        log.debug(String.format("Query \'%s\' passed validation", request));
    }

    /**
     * Validate request value.
     *
     * @param normalizedMin the request being checked
     * @param normalizedMax the request being checked
     * @param min the minimum number accepted
     * @param max the maximum number accepted
     * @throws InvalidRequestException if request did not pass check
     * @return void
     */
    public static void validate(String normalizedMin, String normalizedMax, int min, int max) {
        // check for null, empty, or only spaces
        if (StringUtils.isBlank(normalizedMin) || StringUtils.isBlank(normalizedMax)) {
            log.warn(String.format("Query \'%s\' or \'%s\' is blank", normalizedMin, normalizedMax));
            throw new InvalidRequestException("Query must contain a value");
        }
        // check for valid integer
        if (!isInteger(normalizedMin) || !isInteger(normalizedMax)) {
            log.warn(String.format("Query \'%s\' or \'%s\' is not an integer", normalizedMin, normalizedMax));
            throw new InvalidRequestException("Query must be an integer");
        }
        // check that number is within range
        if (!isInRange(min, max, Integer.parseInt(normalizedMin)) || !isInRange(min, max, Integer.parseInt(normalizedMax))) {
            log.warn(String.format("Query \'%s\' or \'%s\' is not within range of %d-%d", normalizedMin, normalizedMax, min, max));
            throw new InvalidRequestException(String.format("Query must be within range of %d-%d", min, max));
        }
        log.debug(String.format("Query \'%s\' - \'%s\' passed validation", normalizedMin, normalizedMax));
    }

    /**
     * Check if request is an integer.
     *
     * @param request the value being checked
     * @return boolean
     */
    private static boolean isInteger(String request) {
        return StringUtils.isNumeric(request);
    }

    /**
     * Check if request is within range.
     *
     * @param request the value being checked
     * @return boolean
     */
    private static boolean isInRange(int min, int max, int request) {
        return min <= request && request <= max;
    }

}
