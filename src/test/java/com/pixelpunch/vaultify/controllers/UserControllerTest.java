package com.pixelpunch.vaultify.controllers;

import com.pixelpunch.vaultify.core.cache.InMemoryCache;
import com.pixelpunch.vaultify.core.mapper.UserMapper;
import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.core.service.implementations.UserService;
import com.pixelpunch.vaultify.web.controllers.UserController;
import com.pixelpunch.vaultify.web.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private InMemoryCache<Long, UserDto> inMemoryCache;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_UserFoundInCache() {
        Long userId = 1L;
        UserDto cachedUser = new UserDto();
        when(inMemoryCache.get(userId)).thenReturn(cachedUser);

        ResponseEntity<UserDto> result = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(cachedUser, result.getBody());
    }

    @Test
    void testGetUserById_UserFoundInService() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        when(userService.getUserById(userId)).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userDto, result.getBody());
        verify(inMemoryCache, times(1)).put(userId, userDto);
    }

    @Test
    void testGetUserById_UserNotFound() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(null);

        ResponseEntity<UserDto> result = userController.getUserById(userId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(null, result.getBody());
    }

    @Test
    void testGetPublicKey() {
        Long userId = 1L;
        String publicKey = "publicKey";
        when(userService.getPublicKey(userId)).thenReturn(publicKey);

        String result = userController.getPublicKey(userId);

        assertEquals(publicKey, result);
    }

    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto();
        UserDto createdUserDto = new UserDto();
        when(userService.createUser(userDto)).thenReturn(createdUserDto);

        ResponseEntity<UserDto> result = userController.createUser(userDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(createdUserDto, result.getBody());
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        UserDto updatedUserDto = new UserDto();
        when(userService.updateUser(userDto)).thenReturn(updatedUserDto);

        ResponseEntity<UserDto> result = userController.updateUser(userId, userDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedUserDto, result.getBody());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        ResponseEntity<Void> expectedResult = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        ResponseEntity<Void> result = userController.deleteUser(userId);

        assertEquals(expectedResult, result);
        verify(userService, times(1)).deleteUser(userId);
        verify(inMemoryCache, times(1)).remove(userId);
    }

    @Test
    void testDeleteUnverifiedUsers() {
        ResponseEntity<Void> expectedResult = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        ResponseEntity<Void> result = userController.deleteUnverifiedUsers();

        assertEquals(expectedResult, result);
        verify(userService, times(1)).deleteUnverifiedUsers(any(Date.class));
    }

    @Test
    void testGetAllUsers() {
        List<UserDto> userDtos = Arrays.asList(new UserDto(), new UserDto());
        when(userService.getAllUsers()).thenReturn(userDtos);

        ResponseEntity<List<UserDto>> result = userController.getAllUsers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userDtos, result.getBody());
    }

    @Test
    void testDeleteFromCache() {
        Long userId = 1L;
        ResponseEntity<Void> expectedResult = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        ResponseEntity<Void> result = userController.deleteFromCache(userId);

        assertEquals(expectedResult, result);
        verify(inMemoryCache, times(1)).remove(userId);
    }

    @Test
    void testGetCacheSize() {
        int cacheSize = 10;
        when(inMemoryCache.getSize()).thenReturn(cacheSize);

        ResponseEntity<Integer> result = userController.getCacheSize();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(cacheSize, result.getBody().intValue());
    }
}

