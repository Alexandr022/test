package com.pixelpunch.vaultify.core.repositories;

import com.pixelpunch.vaultify.core.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
