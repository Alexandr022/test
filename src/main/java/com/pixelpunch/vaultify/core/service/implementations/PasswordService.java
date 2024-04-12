package com.pixelpunch.vaultify.core.service.implementations;

import com.pixelpunch.vaultify.core.mapper.PasswordMapper;
import com.pixelpunch.vaultify.core.model.Password;
import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.core.repositories.PasswordRepository;
import com.pixelpunch.vaultify.core.repositories.UserRepository;
import com.pixelpunch.vaultify.core.service.IPasswordGeneratorService;
import com.pixelpunch.vaultify.web.dto.PasswordDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class PasswordService implements com.pixelpunch.vaultify.core.service.IPasswordService {
    private final PasswordRepository passwordRepository;
    private final UserRepository userRepository;
    private final PasswordMapper passwordMapper;
    private final IPasswordGeneratorService passwordGeneratorService;

    @Override
    public List<Password> getAllPasswords() {
        return passwordRepository.findAll();
    }

    @Override
    public PasswordDto createPassword(Long userId, PasswordDto passwordDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        passwordDTO.setPassword(passwordGeneratorService.generatePassword(passwordDTO.getLength(), passwordDTO.isIncludeUppercase(), passwordDTO.isIncludeNumbers(), passwordDTO.isIncludeSpecial()));
        Password password = passwordMapper.dtoToPasswords(passwordDTO);
        password.setOwner(user);
        Date date = new Date();
        password.setGeneratedTime(date);
        password = passwordRepository.save(password);
        return PasswordMapper.passwordToDTO(password);
    }

    @Override
    public PasswordDto getPasswordById(Long id) {
        Password password = passwordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Password not found"));
        return PasswordMapper.passwordToDTO(password);
    }

    @Override
    public PasswordDto updatePassword(Long id, PasswordDto updatedPasswordDTO) {
        Password existingPassword = passwordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Password not found"));
        Password updatedPassword = passwordMapper.dtoToPasswords(updatedPasswordDTO);
        existingPassword.setPasswords(updatedPassword.getPasswords());
        existingPassword = passwordRepository.save(existingPassword);
        return PasswordMapper.passwordToDTO(existingPassword);
    }

    @Override
    public void deletePassword(Long id) {
        passwordRepository.deleteById(id);
    }
}
