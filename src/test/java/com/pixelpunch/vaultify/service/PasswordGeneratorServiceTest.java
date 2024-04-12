package com.pixelpunch.vaultify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pixelpunch.vaultify.core.service.implementations.PasswordGeneratorService;
import org.junit.jupiter.api.Test;

class PasswordGeneratorServiceTest {
    @Test
     void testGeneratePassword() {
        PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();
        String password = passwordGeneratorService.generatePassword(8, true, true, false);
        assertEquals(8, password.length());
    }

    @Test
    void testGeneratePasswordWithAllOptions() {
        PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();
        String password = passwordGeneratorService.generatePassword(12, true, true, true);
        assertEquals(12, password.length());
    }
}
