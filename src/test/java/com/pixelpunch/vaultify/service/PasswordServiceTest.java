package com.pixelpunch.vaultify.service;

import com.pixelpunch.vaultify.core.service.implementations.PasswordService;
import com.pixelpunch.vaultify.core.mapper.PasswordMapper;
import com.pixelpunch.vaultify.core.model.Password;
import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.core.repositories.PasswordRepository;
import com.pixelpunch.vaultify.core.repositories.UserRepository;
import com.pixelpunch.vaultify.core.service.IPasswordGeneratorService;
import com.pixelpunch.vaultify.web.dto.PasswordDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PasswordServiceTest {

    @Mock
    private PasswordRepository passwordRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordMapper passwordMapper;

    @Mock
    private IPasswordGeneratorService passwordGeneratorService;

    @InjectMocks
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPasswords() {
        List<Password> passwords = new ArrayList<>();
        when(passwordRepository.findAll()).thenReturn(passwords);

        List<Password> result = passwordService.getAllPasswords();

        assertEquals(passwords, result);
    }

    @Test
    void testDeletePassword() {
        Long id = 1L;

        passwordService.deletePassword(id);

        verify(passwordRepository, times(1)).deleteById(id);
    }
}

