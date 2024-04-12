package com.pixelpunch.vaultify.core.service;

import com.pixelpunch.vaultify.core.model.Password;
import com.pixelpunch.vaultify.web.dto.PasswordDto;

import java.util.List;

public interface IPasswordService {

    List<Password> getAllPasswords();

    PasswordDto createPassword(Long userId, PasswordDto passwordDTO);

    PasswordDto getPasswordById(Long id);

    PasswordDto updatePassword(Long id, PasswordDto updatedPasswordDTO);

    void deletePassword(Long id);
}
