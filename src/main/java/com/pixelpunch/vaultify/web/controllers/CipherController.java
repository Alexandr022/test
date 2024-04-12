package com.pixelpunch.vaultify.web.controllers;

import com.pixelpunch.vaultify.core.model.Cipher;
import com.pixelpunch.vaultify.core.service.implementations.CipherService;
import com.pixelpunch.vaultify.web.dto.CipherDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/ciphers")
public class CipherController {
    private final CipherService cipherService;

    @GetMapping
    public List<Cipher> getAllCiphers() {
        return cipherService.getAllCiphers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cipher> getCipherById(@PathVariable Long id) {
        return cipherService.getCipherById(id);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createCipher(@Valid @RequestBody CipherDto cipherRequestDTO, @PathVariable Long userId) {
        try {
            ResponseEntity<String> response = cipherService.createCipher(cipherRequestDTO, userId);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{cipherId}")
    public ResponseEntity<CipherDto> updateCipher(@PathVariable Long cipherId, @RequestBody CipherDto updatedCipherDto) throws Exception {
        return cipherService.updateCipher(cipherId, updatedCipherDto);
    }

    @DeleteMapping("/{cipherId}")
    public ResponseEntity<Void> deleteCipher(@PathVariable Long cipherId) {
        return cipherService.deleteCipher(cipherId);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Cipher>> getCiphersByOwnerId(@PathVariable Long ownerId) {
        List<Cipher> cipherDTOs = cipherService.getCiphersByOwnerId(ownerId);
        return ResponseEntity.ok(cipherDTOs);
    }

    @PostMapping("/bulk-create/{userId}")
    public ResponseEntity<String> createCipherBulk(@RequestBody List<CipherDto> cipherDtoList, @PathVariable Long userId) {
        try {
            cipherService.createCipherBulk(cipherDtoList, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Bulk cipher creation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/bulk-update")
    public ResponseEntity<String> updateCipherBulk(@RequestBody List<CipherDto> cipherDtoList) {
        try {
            cipherService.updateCipherBulk(cipherDtoList);
            return ResponseEntity.status(HttpStatus.OK).body("Bulk cipher update successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}

