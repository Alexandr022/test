package com.pixelpunch.vaultify.core.service.implementations;

import com.pixelpunch.vaultify.core.service.IPasswordGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Service
public class PasswordGeneratorService implements IPasswordGeneratorService {
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";

    @Override
    public String generatePassword(int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial) {
        if (length < 1) {
            throw new IllegalArgumentException("Password length must be greater than zero");
        }

        StringBuilder password = new StringBuilder();
        String allCharacters = LOWERCASE_CHARACTERS + (includeUppercase ? UPPERCASE_CHARACTERS : "")
                + (includeNumbers ? NUMERIC_CHARACTERS : "") + (includeSpecial ? SPECIAL_CHARACTERS : "");

        for (int i = 0; i < length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(allCharacters.length());
            char randomChar = allCharacters.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

}
