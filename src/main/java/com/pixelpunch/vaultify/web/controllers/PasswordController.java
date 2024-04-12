package com.pixelpunch.vaultify.web.controllers;

import com.pixelpunch.vaultify.core.model.Password;
import com.pixelpunch.vaultify.core.service.IPasswordGeneratorService;
import com.pixelpunch.vaultify.core.service.IPasswordService;
import com.pixelpunch.vaultify.web.dto.PasswordDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/password")
public class PasswordController {
    private final IPasswordGeneratorService passwordGeneratorService;
    private final IPasswordService passwordService;

    @GetMapping
    public PasswordDto generatePassword(@RequestParam(name = "length", defaultValue = "12") int length,
                                        @RequestParam(name = "includeUppercase", defaultValue = "true") boolean includeUppercase,
                                        @RequestParam(name = "includeNumbers", defaultValue = "true") boolean includeNumbers,
                                        @RequestParam(name = "includeSpecial", defaultValue = "true") boolean includeSpecial) {

        String generatedPassword = passwordGeneratorService.generatePassword(length, includeUppercase, includeNumbers, includeSpecial);
        return new PasswordDto(generatedPassword, length, includeUppercase, includeNumbers, includeSpecial);
    }

    @GetMapping("/all")
    public List<Password> getAllPasswords() {
       return passwordService.getAllPasswords();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PasswordDto> createPassword(@PathVariable Long userId, @RequestBody PasswordDto passwordDTO) {
        PasswordDto createdPassword = passwordService.createPassword(userId, passwordDTO);
        return new ResponseEntity<>(createdPassword, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordDto> getPasswordById(@PathVariable Long id) {
        PasswordDto passwordDto = passwordService.getPasswordById(id);
        return ResponseEntity.ok(passwordDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasswordDto> updatePassword(@PathVariable Long id, @RequestBody PasswordDto updatedPasswordDTO) {
        PasswordDto updatedPassword = passwordService.updatePassword(id, updatedPasswordDTO);
        return ResponseEntity.ok(updatedPassword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassword(@PathVariable Long id) {
        passwordService.deletePassword(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
