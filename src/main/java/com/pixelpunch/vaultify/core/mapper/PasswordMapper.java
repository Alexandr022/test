package com.pixelpunch.vaultify.core.mapper;

import com.pixelpunch.vaultify.core.model.Password;
import com.pixelpunch.vaultify.web.dto.PasswordDto;
import org.springframework.stereotype.Component;

@Component
public class PasswordMapper {
    private PasswordMapper() {}
    public static PasswordDto passwordToDTO(Password password) {
        return new PasswordDto(
                password.getId(),
                password.getPasswords(),
                password.getLength(),
                password.isIncludeUppercases(),
                password.isIncludeNumbers(),
                password.isIncludeSpecials(),
                password.getGeneratedTime(),
                password.getOwner()
        );
    }

    public static Password dtoToPasswords(PasswordDto passwordDTO) {
        return new Password(
                passwordDTO.getId(),
                passwordDTO.getLength(),
                passwordDTO.isIncludeUppercase(),
                passwordDTO.isIncludeNumbers(),
                passwordDTO.isIncludeSpecial(),
                passwordDTO.getGeneratedTime(),
                passwordDTO.getPassword(),
                passwordDTO.getUser()
                );
    }

}


