package com.pixelpunch.vaultify.core.service.implementations;

import com.pixelpunch.vaultify.core.mapper.CipherMapper;
import com.pixelpunch.vaultify.web.dto.CipherDto;
import lombok.extern.slf4j.Slf4j;
import com.pixelpunch.vaultify.core.model.Cipher;
import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.core.repositories.CipherRepository;
import com.pixelpunch.vaultify.core.repositories.UserRepository;
import com.pixelpunch.vaultify.core.utils.RSAEncryption;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class CipherService implements com.pixelpunch.vaultify.core.service.ICipherService {

    private final CipherRepository cipherRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Cipher> getCipherById(Long cipherId) {
        Cipher cipher = cipherRepository.findById(cipherId).orElse(null);
        if (cipher == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            String privateKeyString = userRepository.findPrivateKeyByUserId(cipher.getOwner().getId());
            if (privateKeyString == null) {
                log.error("Private key not found for user {}", cipher.getOwner().getId());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            PrivateKey privateKey = RSAEncryption.getPrivateKeyFromString(privateKeyString);
            byte[] encryptedData = Base64.getDecoder().decode(cipher.getData());

            String decryptedData = RSAEncryption.decrypt(encryptedData, privateKey);
            cipher.setData(decryptedData);
        } catch (IllegalArgumentException e) {
            log.error("Illegal base64 character error: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            log.error("Error decrypting data: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok().body(cipher);
    }

    @Override
    public ResponseEntity<String> createCipher(CipherDto cipherDto, Long userId) {
        if (cipherDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cipher data cannot be null");
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with the specified ID not found");
        }

        String publicKeyString = userRepository.findPublicKeyById(userId);
        if (publicKeyString == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User's public key not found");
        }

        try {
            log.info("User ID: {}", userId);
            log.info("Encrypted Account Data: {}", cipherDto.getData());

            if (cipherDto.getData() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cipher data cannot be null");
            }

            PublicKey publicKey = RSAEncryption.getPublicKeyFromString(publicKeyString);
            log.info("Public Key: {}", publicKeyString);

            Cipher cipher = new Cipher();
            cipher.setData(cipherDto.getData());

            byte[] encryptedBytes = RSAEncryption.encrypt(cipherDto.getData(), publicKey);

            String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);
            if (encryptedData.length() > 255) {
                encryptedData = encryptedData.substring(0, 255);
            }
            log.info("Encrypted Data: {}", encryptedData);

            cipher.setOwner(user);
            cipher.setData(encryptedData);
            cipher.setCreated(new Date());
            cipher.setLastModified(new Date());

            cipherRepository.save(cipher);

            return ResponseEntity.status(HttpStatus.CREATED).body("Cipher successfully created");
        } catch (Exception e) {
            log.error("Error creating cipher", e);
            log.error("Error stack trace: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating cipher");
        }
    }

    @Override
    public ResponseEntity<CipherDto> updateCipher(Long cipherId, CipherDto updatedCipherDTO) {
        Cipher existingCipher = cipherRepository.findById(cipherId).orElse(null);
        if (existingCipher == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Long userId = existingCipher.getOwner().getId();
            String publicKeyString = userRepository.findPublicKeyById(userId);
            PublicKey publicKey = RSAEncryption.getPublicKeyFromString(publicKeyString);

            byte[] encryptedBytes = RSAEncryption.encrypt(updatedCipherDTO.getData(), publicKey);
            String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);
            if (encryptedData.length() > 255) {
                encryptedData = encryptedData.substring(0, 255);
            }

            existingCipher.setData(encryptedData);
            existingCipher.setLastModified(new Date());
            cipherRepository.save(existingCipher);

            CipherDto updatedCipherDto = CipherMapper.cipherToDTO(existingCipher);
            return ResponseEntity.ok().body(updatedCipherDto);
        } catch (Exception e) {
            log.error("Error updating cipher", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Void> deleteCipher(Long cipherId) {
        Cipher cipher = cipherRepository.findById(cipherId).orElse(null);
        if (cipher == null) {
            return ResponseEntity.notFound().build();
        }
        cipherRepository.delete(cipher);
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<Cipher> getAllCiphers() {
        return cipherRepository.findAll();
    }

    @Override
    public List<Cipher> getCiphersByOwnerId(Long ownerId) {
        return cipherRepository.findByOwnerId(ownerId);
    }

    public void createCipherBulk(List<CipherDto> cipherDtoList, Long userId) {
        List<String> errors = new ArrayList<>();
        cipherDtoList.forEach(cipherDto -> {
            try {
                createCipher(cipherDto, userId);
            } catch (Exception e) {
                errors.add("Error creating cipher: " + e.getMessage());
            }
        });
    }

    public void updateCipherBulk(List<CipherDto> cipherDtoList) {
        List<String> errors = new ArrayList<>();
        cipherDtoList.forEach(cipherDto -> {
            try {
                Long cipherId = cipherDto.getId();
                if (cipherId != null) {
                    updateCipher(cipherId, cipherDto);
                } else {
                    errors.add("Error updating cipher: No ID provided for cipher");
                }
            } catch (Exception e) {
                errors.add("Error updating cipher: " + e.getMessage());
            }
        });
    }

}
