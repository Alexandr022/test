package com.pixelpunch.vaultify.controllers;

import com.pixelpunch.vaultify.core.model.Password;
import com.pixelpunch.vaultify.core.service.IPasswordGeneratorService;
import com.pixelpunch.vaultify.core.service.IPasswordService;
import com.pixelpunch.vaultify.web.controllers.PasswordController;
import com.pixelpunch.vaultify.web.dto.PasswordDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PasswordControllerTest {

    @Mock
    private IPasswordGeneratorService passwordGeneratorService;

    @Mock
    private IPasswordService passwordService;

    @InjectMocks
    private PasswordController passwordController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePassword() {
        // Arrange
        int length = 12;
        boolean includeUppercase = true;
        boolean includeNumbers = true;
        boolean includeSpecial = true;
        String generatedPassword = "generatedPassword";
        when(passwordGeneratorService.generatePassword(length, includeUppercase, includeNumbers, includeSpecial)).thenReturn(generatedPassword);

        // Act
        PasswordDto result = passwordController.generatePassword(length, includeUppercase, includeNumbers, includeSpecial);

        // Assert
        assertEquals(generatedPassword, result.getPassword());
        assertEquals(length, result.getLength());
        assertEquals(includeUppercase, result.isIncludeUppercase());
        assertEquals(includeNumbers, result.isIncludeNumbers());
        assertEquals(includeSpecial, result.isIncludeSpecial());
    }

    @Test
    void testGetAllPasswords() {
        // Arrange
        List<Password> passwordList = Arrays.asList(new Password(), new Password());
        when(passwordService.getAllPasswords()).thenReturn(passwordList);

        // Act
        List<Password> result = passwordController.getAllPasswords();

        // Assert
        assertEquals(passwordList, result);
    }

    @Test
    void testCreatePassword() {
        // Arrange
        Long userId = 1L;
        PasswordDto passwordDto = new PasswordDto();
        when(passwordService.createPassword(userId, passwordDto)).thenReturn(passwordDto);

        // Act
        ResponseEntity<PasswordDto> result = passwordController.createPassword(userId, passwordDto);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(passwordDto, result.getBody());
    }

    @Test
    void testGetPasswordById() {
        // Arrange
        Long passwordId = 1L;
        PasswordDto passwordDto = new PasswordDto();
        when(passwordService.getPasswordById(passwordId)).thenReturn(passwordDto);

        // Act
        ResponseEntity<PasswordDto> result = passwordController.getPasswordById(passwordId);

        // Assert
        assertEquals(passwordDto, result.getBody());
    }

    @Test
    void testUpdatePassword() {
        // Arrange
        Long passwordId = 1L;
        PasswordDto updatedPasswordDto = new PasswordDto();
        when(passwordService.updatePassword(passwordId, updatedPasswordDto)).thenReturn(updatedPasswordDto);

        // Act
        ResponseEntity<PasswordDto> result = passwordController.updatePassword(passwordId, updatedPasswordDto);

        // Assert
        assertEquals(updatedPasswordDto, result.getBody());
    }

    @Test
    void testDeletePassword() {
        // Arrange
        Long passwordId = 1L;

        // Act
        ResponseEntity<Void> result = passwordController.deletePassword(passwordId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(passwordService, times(1)).deletePassword(passwordId);
    }
}

