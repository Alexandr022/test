package com.pixelpunch.vaultify.core.service;

import com.pixelpunch.vaultify.core.model.Cipher;
import com.pixelpunch.vaultify.core.service.implementations.CipherService;
import com.pixelpunch.vaultify.web.dto.CipherDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICipherService {
    ResponseEntity<Cipher> getCipherById(Long cipherId);

    ResponseEntity<String> createCipher(CipherDto cipherDto, Long userId);

    ResponseEntity<CipherDto> updateCipher(Long cipherId, CipherDto updatedCipherDTO);

    ResponseEntity<Void> deleteCipher(Long cipherId);

    List<Cipher> getAllCiphers();

    List<Cipher> getCiphersByOwnerId(Long ownerId);
}
