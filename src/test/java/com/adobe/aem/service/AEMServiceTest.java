package com.adobe.aem.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AEMServiceTest {

    @Test
    void testConvert() {
        AEMService service = new AEMService();
        assertEquals("I", service.convert("1", 1, 3999));
        assertEquals("V", service.convert("5", 1, 3999));
        assertEquals("XXVII", service.convert("27", 1, 3999));
        assertEquals("XLV", service.convert("45", 1, 3999));
        assertEquals("LXXXIX", service.convert("89", 1, 3999));
        assertEquals("XCI", service.convert("91", 1, 3999));
        assertEquals("CI", service.convert("101", 1, 3999));
        assertEquals("CDL", service.convert("450", 1, 3999));
        assertEquals("DCC", service.convert("700", 1, 3999));
        assertEquals("CMXL", service.convert("940", 1, 3999));
        assertEquals("MMMCMXCIX", service.convert("3999", 1, 3999));
        assertNull(service.convert("0", 1, 3999));
        assertNull(service.convert("4000", 1, 3999));
        assertNull(service.convert("-1", 1, 3999));

        // for range
        assertNull(service.convert("0","4000", 1, 3999));
    }

}