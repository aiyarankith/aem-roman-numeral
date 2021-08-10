package com.adobe.aem;

import com.adobe.aem.controller.AEMController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AEMApplicationTest {

    @Autowired
    private AEMController controller;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

}