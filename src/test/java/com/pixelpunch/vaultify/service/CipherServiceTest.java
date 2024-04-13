package com.pixelpunch.vaultify.service;

import com.pixelpunch.vaultify.core.mapper.CipherMapper;
import com.pixelpunch.vaultify.core.model.Cipher;
import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.core.repositories.CipherRepository;
import com.pixelpunch.vaultify.core.repositories.UserRepository;
import com.pixelpunch.vaultify.core.service.implementations.CipherService;
import com.pixelpunch.vaultify.web.dto.CipherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CipherServiceTest {

    @Mock
    private CipherRepository cipherRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CipherService cipherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteCipher() {
        // Arrange
        Long cipherId = 1L;
        Cipher cipher = new Cipher();
        cipher.setId(cipherId);

        when(cipherRepository.findById(cipherId)).thenReturn(Optional.of(cipher));

        // Act
        ResponseEntity<Void> responseEntity = cipherService.deleteCipher(cipherId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(cipherRepository, times(1)).delete(cipher);
    }

    @Test
    void testGetAllCiphers() {
        // Arrange
        List<Cipher> cipherList = Arrays.asList(new Cipher(), new Cipher());
        when(cipherRepository.findAll()).thenReturn(cipherList);

        // Act
        List<Cipher> result = cipherService.getAllCiphers();

        // Assert
        assertEquals(cipherList.size(), result.size());
        assertEquals(cipherList, result);
    }

    @Test
    void testGetCiphersByOwnerId() {
        // Arrange
        Long ownerId = 1L;
        List<Cipher> cipherList = Arrays.asList(new Cipher(), new Cipher());
        when(cipherRepository.findByOwnerId(ownerId)).thenReturn(cipherList);

        // Act
        List<Cipher> result = cipherService.getCiphersByOwnerId(ownerId);

        // Assert
        assertEquals(cipherList.size(), result.size());
        assertEquals(cipherList, result);
    }

}

