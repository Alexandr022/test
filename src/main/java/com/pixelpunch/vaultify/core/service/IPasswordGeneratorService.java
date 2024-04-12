package com.pixelpunch.vaultify.core.service;

public interface IPasswordGeneratorService {
    String generatePassword(int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial);
}
