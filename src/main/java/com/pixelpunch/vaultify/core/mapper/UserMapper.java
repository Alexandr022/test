package com.pixelpunch.vaultify.core.mapper;

import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.web.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    private UserMapper() {}
    public static UserDto userToDTO(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.isEmailVerified(),
                user.getEmailVerificationCode(),
                user.getEmailVerificationCodeExpiresAt(),
                user.getPassword(),
                user.getPrivateKey(),
                user.getPublicKey(),
                user.getPasswordHint(),
                user.isTwoFactorEnabled(),
                user.getTwoFactorVerificationCode(),
                user.getCreated(),
                user.getLastPasswordChange(),
                user.getCiphers() != null ? user.getCiphers().stream().map(CipherMapper::cipherToDTO).toList() : null,
                user.getPasswords() != null ? user.getPasswords().stream().map(PasswordMapper::passwordToDTO).toList() : null
        );
    }

    public static User dtoToUser(UserDto userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getEmail(),
                userDTO.isEmailVerified(),
                userDTO.getEmailVerificationCode(),
                userDTO.getEmailVerificationCodeExpiresAt(),
                userDTO.getPassword(),
                userDTO.getPrivateKey(),
                userDTO.getPublicKey(),
                userDTO.getPasswordHint(),
                userDTO.isTwoFactorEnabled(),
                userDTO.getTwoFactorVerificationCode(),
                userDTO.getCreated(),
                userDTO.getLastPasswordChange(),
                userDTO.getCiphers() != null ? userDTO.getCiphers().stream().map(CipherMapper::dtoToCipher).toList() : null,
                userDTO.getPasswords() != null ? userDTO.getPasswords().stream().map(PasswordMapper::dtoToPasswords).toList() : null
        );
    }

    public static List<UserDto> usersToDTO(List<User> users) {
        return users.stream().map(UserMapper::userToDTO).toList();
    }
}
