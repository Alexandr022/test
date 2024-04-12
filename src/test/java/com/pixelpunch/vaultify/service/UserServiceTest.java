package com.pixelpunch.vaultify.service;

import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.core.service.implementations.UserService;
import com.pixelpunch.vaultify.core.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUser(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testGetPublicKey() {
        String publicKey = "publicKey";
        when(userRepository.findPublicKeyById(1L)).thenReturn(publicKey);
        String result = userService.getPublicKey(1L);
        assertEquals(publicKey, result);
    }

    @Test
    void testDeleteUnverifiedUsers() {
        userService.deleteUnverifiedUsers(null);
        verify(userRepository).deleteUnverifiedUsers(null);
    }

    @Test
    void testGetUsersByEmailVerified() {
        List<User> users = Collections.singletonList(new User());
        when(userRepository.findByEmailVerifiedCustomQuery(true)).thenReturn(users);
        List<User> result = userService.getUsersByEmailVerified(true);
        assertEquals(users, result);
    }
}

