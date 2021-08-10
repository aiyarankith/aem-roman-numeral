package com.adobe.aem.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.util.Objects;

/**
 * This is the domain object to be returned in the response.
 *
 * @author  Ankith Aiyar
 * @version 1.0
 */
@Slf4j
public class Numeral {

    @JsonProperty("input")
    private final String integer;
    @JsonProperty("output")
    private final String roman;

    public Numeral(String integer, String roman) {
        this.integer = integer;
        this.roman = roman;
    }

    public String getInteger() {
        return integer;
    }

    public String getRoman() {
        return roman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Numeral)) {
            return false;
        }
        Numeral numeral = (Numeral) o;
        return Objects.equals(this.integer, numeral.integer) && Objects.equals(this.roman, numeral.roman);
    }

    @Override
    public String toString() {
        return String.format("Numeral{integer=\"%s\", roman=\"%s\"}", this.integer, this.roman);
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("input", this.integer);
        jsonObject.put("output", this.roman);
        return jsonObject;
    }

}
