package com.adobe.aem.controller;

import com.adobe.aem.domain.Numeral;
import com.adobe.aem.service.AEMService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AEMController.class)
class AEMControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AEMService service;

    @Test
    void testIndex() throws Exception {
        String expected = "Adobe & AEM Engineering Test.<br>GET http://localhost:8080/romannumeral?query={integer}";
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void testConvertToRomanNumeral_HappyPath() throws Exception {
        when(service.convert("5", 1, 3999)).thenReturn("V");
        String expected = "{\"output\":\"V\",\"input\":\"5\"}";
        mockMvc.perform(get("/romannumeral?query=5")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void testConvertToRomanNumeral_HappyPathWithSpaces() throws Exception {
        when(service.convert("3999", 1, 3999)).thenReturn("MMMCMXCIX");
        String expected = "{\"output\":\"MMMCMXCIX\",\"input\":\" 3999 \"}";
        mockMvc.perform(get("/romannumeral?query= 3999 ")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void testConvertToRomanNumeral_EmptyQuery() throws Exception {
        String expected = "400 BAD_REQUEST \"Query must contain a value\"";
        mockMvc.perform(get("/romannumeral?query=   ")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    void testConvertToRomanNumeral_OutOfRange() throws Exception {
        String expected = "400 BAD_REQUEST \"Query must be within range of 1-3999\"";
        mockMvc.perform(get("/romannumeral?query=4000")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    void testConvertToRomanNumeral_NonInteger() throws Exception {
        String expected = "400 BAD_REQUEST \"Query must be an integer\"";
        mockMvc.perform(get("/romannumeral?query=abc")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    void testConvertToRomanNumeral_UnsupportedMethod() throws Exception {
        String expected = "Request method 'POST' not supported";
        mockMvc.perform(post("/romannumeral?query=5")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    void testConvertToRomanNumeral_Range_HappyPath() throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<Numeral> numeralList = new ArrayList<>();
        numeralList.add(new Numeral("5", "V"));
        numeralList.add(new Numeral("6", "VI"));
        numeralList.add(new Numeral("7", "VII"));
        jsonObject.put("conversions",numeralList);

        when(service.convert("5","7",1, 3999)).thenReturn(jsonObject);
        String expected = "{\"conversions\":[{\"input\":\"5\",\"output\":\"V\"},{\"input\":\"6\",\"output\":\"VI\"},{\"input\":\"7\",\"output\":\"VII\"}]}";
        mockMvc.perform(get("/romannumeral?min=5&max=7")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }
}