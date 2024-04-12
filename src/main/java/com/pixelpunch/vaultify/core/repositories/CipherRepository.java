package com.pixelpunch.vaultify.core.repositories;

import com.pixelpunch.vaultify.core.model.Cipher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CipherRepository extends JpaRepository<Cipher, Long> {
    List<Cipher> findByOwnerId(Long ownerId);
}