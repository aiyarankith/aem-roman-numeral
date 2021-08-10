package com.adobe.aem.controller;

import com.adobe.aem.domain.Numeral;
import com.adobe.aem.service.AEMService;
import com.adobe.aem.util.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * This is the controller class that handles the web calls for this application.
 * Only supported method is GET. URI is /romannumeral?query={integer}
 * Range is currently set to 1-3999.
 *
 * @author  Ankith Aiyar
 * @version 1.0
 */
@RestController
@Slf4j
public class AEMController {

    /**
     * Configurable minimum value defined in application.properties
     */
    @Value("${romannumeral.number.min:1}")
    private int minRange;

    /**
     * Configurable maximum value defined in application.properties
     */
    @Value("${romannumeral.number.max:3999}")
    private int maxRange;

    @Autowired
    private AEMService service;

    /**
     * Default home landing page.
     *
     * @return String welcome message
     */
    @RequestMapping("/")
    public String index() {
        return "Adobe & AEM Engineering Test.<br>GET http://localhost:8080/romannumeral?query={integer}";
    }

    /**
     * GET call to convert a number to a Roman numeral.
     *
     * @param query the input number to be converted
     * @param min the input number to be converted
     * @param max the input number to be converted
     *
     * @return String the JSON number input and Roman numeral output value
     * @throws ResponseStatusException if it is on invalid request value
     */
    @ResponseBody
    @GetMapping("/romannumeral")
    public JSONObject convertToRomanNumeral(@RequestParam(value = "query", required = false) String query,
                                            @RequestParam(value = "min", required = false) String min,
                                            @RequestParam(value = "max", required = false) String max) {
        try {
            log.debug(String.format("Received request to convert \'%s\' to a Roman numeral", query));
            if (min != null && max != null) {
                log.debug(String.format("Received request to convert Roman numeral from \'%s\' to \'%s\'", min, max));
                // normalizing input by removing leading and trailing spaces
                String normalizedMin = min.trim();
                String normalizedMax = max.trim();
                RequestValidator.validate(normalizedMin, normalizedMax, minRange, maxRange);
                JSONObject romanNumeral = service.convert(normalizedMin, normalizedMax, minRange, maxRange);
                log.debug(String.format("Finished converting \'%s\' to \'%s\'", normalizedMin, normalizedMax));
                return romanNumeral;
            } else {
                // normalizing input by removing leading and trailing spaces
                String normalized = query.trim();
                RequestValidator.validate(normalized, minRange, maxRange);
                String romanNumeral = service.convert(normalized, minRange, maxRange);
                log.debug(String.format("Finished converting \'%s\' to \'%s\'", query, romanNumeral));
                return new Numeral(query, romanNumeral).toJsonObject();
            }

        // catch all exceptions, do not leave any unhandled
        } catch (Exception e) {
            // track unsuccessful requests
            log.error(String.format("Failed to convert \'%s\' to a Roman numeral", query), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
