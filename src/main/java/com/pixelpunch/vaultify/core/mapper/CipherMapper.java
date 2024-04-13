package com.pixelpunch.vaultify.core.mapper;

import com.pixelpunch.vaultify.core.model.Cipher;
import com.pixelpunch.vaultify.web.dto.CipherDto;
import org.springframework.stereotype.Component;

@Component
public class CipherMapper {
    private CipherMapper() {}
    public static CipherDto cipherToDTO(Cipher cipher) {
        return new CipherDto(
                cipher.getId(),
                cipher.getOwner(),
                cipher.getData(),
                cipher.isFavorite(),
                cipher.getCreated(),
                cipher.getLastModified()
        );
    }

    public static Cipher dtoToCipher(CipherDto cipherDTO) {
        return new Cipher(
                cipherDTO.getId(),
                cipherDTO.getOwner(),
                cipherDTO.getData(),
                cipherDTO.isFavorite(),
                cipherDTO.getCreated(),
                cipherDTO.getLastModified()
        );
    }

}
