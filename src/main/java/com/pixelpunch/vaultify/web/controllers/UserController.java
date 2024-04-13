package com.pixelpunch.vaultify.web.controllers;

import com.pixelpunch.vaultify.core.cache.InMemoryCache;
import com.pixelpunch.vaultify.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import com.pixelpunch.vaultify.core.mapper.UserMapper;
import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.core.service.implementations.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final InMemoryCache<Long, UserDto> inMemoryCache;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        log.info("Attempting to fetch user by ID: {}", userId);

        UserDto cachedUser = inMemoryCache.get(userId);
        if (cachedUser != null) {
            log.info("User found by ID: {} in cache", userId);
            return new ResponseEntity<>(cachedUser, HttpStatus.OK);
        }

        UserDto userDTO = userService.getUserById(userId);

        if (userDTO != null) {
            inMemoryCache.put(userId, userDTO);
            log.info("User found by ID: {}", userId);
        } else {
            log.info("User not found by ID: {}", userId);
        }

        return userDTO != null ? new ResponseEntity<>(userDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users-by-email")
    public ResponseEntity<List<UserDto>> getUsersByEmailVerified(@RequestParam boolean emailVerified) {
        log.info("Attempting to fetch users by email verification status: {}", emailVerified);

        List<User> users = userService.getUsersByEmailVerified(emailVerified);
        List<UserDto> userDtos = UserMapper.usersToDTO(users);

        log.info("Fetched {} users by email verification status: {}", userDtos.size(), emailVerified);

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/public-key")
    public String getPublicKey(@RequestParam Long userId) {
        return userService.getPublicKey(userId);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Creating new user");

        UserDto newUserDto = userService.createUser(userDto);

        log.info("Created new user with ID: {}", newUserDto.getId());

        return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDTO) {
        log.info("Updating user with ID: {}", userId);

        userDTO.setId(userId);
        UserDto updatedUserDto = userService.updateUser(userDTO);

        if (updatedUserDto != null) {
            log.info("Updated user with ID: {}", userId);
        } else {
            log.info("Failed to update user with ID: {}", userId);
        }

        return updatedUserDto != null ? new ResponseEntity<>(updatedUserDto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user with ID: {}", userId);

        userService.deleteUser(userId);
        inMemoryCache.remove(userId);

        log.info("Deleted user with ID: {}", userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/unverified")
    public ResponseEntity<Void> deleteUnverifiedUsers() {
        log.info("Deleting unverified users");

        userService.deleteUnverifiedUsers(new Date());

        log.info("Deleted unverified users");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Fetching all users");

        List<UserDto> userDtoList = userService.getAllUsers();

        log.info("Fetched {} users", userDtoList.size());

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/cache/{userId}")
    public ResponseEntity<Void> deleteFromCache(@PathVariable Long userId) {
        log.info("Deleting user with ID: {} from cache", userId);
        inMemoryCache.remove(userId);
        log.info("Deleted user with ID: {} from cache", userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/cache/size")
    public ResponseEntity<Integer> getCacheSize() {
        int size = inMemoryCache.getSize();
        log.info("Size of cache: {}", size);
        return new ResponseEntity<>(size, HttpStatus.OK);
    }
}
