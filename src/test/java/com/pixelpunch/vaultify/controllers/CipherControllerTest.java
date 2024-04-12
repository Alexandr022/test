package com.pixelpunch.vaultify.controllers;

import com.pixelpunch.vaultify.core.model.Cipher;
import com.pixelpunch.vaultify.core.service.implementations.CipherService;
import com.pixelpunch.vaultify.web.controllers.CipherController;
import com.pixelpunch.vaultify.web.dto.CipherDto;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;

class CipherControllerTest {

    @Mock
    private CipherService cipherService;

    @InjectMocks
    private CipherController cipherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCiphers() {
        // Arrange
        List<Cipher> cipherList = Arrays.asList(new Cipher(), new Cipher());
        when(cipherService.getAllCiphers()).thenReturn(cipherList);

        // Act
        List<Cipher> result = cipherController.getAllCiphers();

        // Assert
        assertEquals(cipherList, result);
    }

    @Test
    void testGetCipherById() {
        // Arrange
        Long cipherId = 1L;
        Cipher cipher = new Cipher();
        when(cipherService.getCipherById(cipherId)).thenReturn(ResponseEntity.ok(cipher));

        // Act
        ResponseEntity<Cipher> result = cipherController.getCipherById(cipherId);

        // Assert
        assertEquals(cipher, result.getBody());
    }

    @Test
    void testCreateCipher() {
        // Arrange
        CipherDto cipherDto = new CipherDto();
        Long userId = 1L;
        when(cipherService.createCipher(cipherDto, userId)).thenReturn(ResponseEntity.status(CREATED).body("Cipher successfully created"));

        // Act
        ResponseEntity<String> result = cipherController.createCipher(cipherDto, userId);

        // Assert
        assertEquals(CREATED, result.getStatusCode());
        assertEquals("Cipher successfully created", result.getBody());
    }

    @Test
    void testUpdateCipher() throws Exception {
        // Arrange
        Long cipherId = 1L;
        CipherDto updatedCipherDto = new CipherDto();
        when(cipherService.updateCipher(cipherId, updatedCipherDto)).thenReturn(ResponseEntity.ok().body(updatedCipherDto));

        // Act
        ResponseEntity<CipherDto> result = cipherController.updateCipher(cipherId, updatedCipherDto);

        // Assert
        assertEquals(updatedCipherDto, result.getBody());
    }

    @Test
    void testDeleteCipher() {
        // Arrange
        Long cipherId = 1L;
        when(cipherService.deleteCipher(cipherId)).thenReturn(ResponseEntity.noContent().build());

        // Act
        ResponseEntity<Void> result = cipherController.deleteCipher(cipherId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void testGetCiphersByOwnerId() {
        // Arrange
        Long ownerId = 1L;
        List<Cipher> cipherList = Arrays.asList(new Cipher(), new Cipher());
        when(cipherService.getCiphersByOwnerId(ownerId)).thenReturn(cipherList);

        // Act
        ResponseEntity<List<Cipher>> result = cipherController.getCiphersByOwnerId(ownerId);

        // Assert
        assertEquals(cipherList, result.getBody());
    }

}

