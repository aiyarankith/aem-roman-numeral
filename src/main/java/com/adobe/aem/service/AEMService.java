package com.adobe.aem.service;

import com.adobe.aem.domain.Numeral;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the service class that handles the business logic of conversion from number to Roman numeral.
 * Range is currently set to 1-3999.
 *
 * @author  Ankith Aiyar
 * @version 1.0
 */
@Component
@Slf4j
public class AEMService {

    // only defined values up to 1000 given the upper limit of 3999
    private static final int[] integers = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private static final String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    /**
     * Convert a number into a Roman numeral.
     *
     * @param integer the input number to be converted
     * @param min the minimum number accepted
     * @param max the maximum number accepted
     * @return String the Roman numeral value
     */
    public String convert(String integer, int min, int max) {
        long startTimer = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();
        int number = Integer.parseInt(integer);
        // return if out of range
        if (number < min || number > max) {
            return null;
        }
        int position = integers.length - 1;
        // append the corresponding Roman numeral place while it is still greater than that place, starting from high to low
        while (number > 0) {
            while (number >= integers[position]) {
                result.append(romanNumerals[position]);
                number -= integers[position];
            }
            position--;
        }
        // log for processing metrics
        log.info(String.format("[TIMER] Convert \'%s\' executed in %d ms", integer, System.currentTimeMillis() - startTimer));
        return result.toString();
    }

    /**
     * Returns range of roman numerals between min and max
     *
     * @param normalizedMin
     * @param normalizedMax
     * @param min
     * @param max
     * @return
     */
    public JSONObject convert(String normalizedMin, String normalizedMax, int min, int max) {
        long startTimer = System.currentTimeMillis();
        JSONObject conversions = new JSONObject();
        List<Numeral> numeralList = new ArrayList<>();

        if (Integer.parseInt(normalizedMin) < min || Integer.parseInt(normalizedMax) > max) {
            return null;
        }

        for (int i = Integer.parseInt(normalizedMin); i <= Integer.parseInt(normalizedMax); i++) {
            String roman = convert(String.valueOf(i), min, max);
            numeralList.add(new Numeral(String.valueOf(i), roman));
        }
        conversions.put("conversions", numeralList);
        // log for processing metrics
        log.info(String.format("[TIMER] Convert \'%s\' to \'%s\' executed in %d ms", normalizedMin, normalizedMax,
                System.currentTimeMillis() - startTimer));
        return conversions;
    }
}
