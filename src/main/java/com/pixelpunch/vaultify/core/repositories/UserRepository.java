package com.pixelpunch.vaultify.core.repositories;

import com.pixelpunch.vaultify.core.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.emailVerified = :emailVerified")
    List<User> findByEmailVerifiedCustomQuery(boolean emailVerified);

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} u WHERE u.emailVerified = false AND u.created < :date")
    void deleteUnverifiedUsers(Date date);

    @Query("SELECT u.privateKey FROM User u WHERE u.id = :userId")
    String findPrivateKeyByUserId(@Param("userId") Long userId);

    @Query("SELECT u.publicKey FROM User u WHERE u.id = :userId")
    String findPublicKeyById(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findUserById(@Param("userId") Long userId);
}