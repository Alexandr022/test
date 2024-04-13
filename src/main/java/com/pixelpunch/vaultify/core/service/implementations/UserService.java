package com.pixelpunch.vaultify.core.service.implementations;

import com.pixelpunch.vaultify.core.mapper.UserMapper;
import com.pixelpunch.vaultify.core.model.User;
import com.pixelpunch.vaultify.core.repositories.UserRepository;
import com.pixelpunch.vaultify.core.service.IUserService;
import com.pixelpunch.vaultify.web.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KeyPairGenerator keyPairGenerator;

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? UserMapper.userToDTO(user) : null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.usersToDTO(users);
    }

    @Override
    public UserDto createUser(UserDto createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setPasswordHint(createUserRequest.getPasswordHint());

        try {
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            user.setPublicKey(publicKeyString);
            user.setPrivateKey(privateKeyString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        User savedUser = userRepository.save(user);
        return UserMapper.userToDTO(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDTO) {
        User user = UserMapper.dtoToUser(userDTO);
        User updatedUser = userRepository.save(user);
        return UserMapper.userToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? UserMapper.userToDTO(user) : null;
    }

    public String getPublicKey(Long userId) {
        return userRepository.findPublicKeyById(userId);
    }

    @Override
    public void deleteUnverifiedUsers(Date date) {
        userRepository.deleteUnverifiedUsers(date);
    }

    @Override
    public List<User> getUsersByEmailVerified(boolean emailVerified) {
        return userRepository.findByEmailVerifiedCustomQuery(emailVerified);
    }

}


