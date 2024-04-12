package com.pixelpunch.vaultify.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    private boolean emailVerified = false;
    private String emailVerificationCode = null;
    private Date emailVerificationCodeExpiresAt = null;

    private String password;

    @Column(columnDefinition = "TEXT")
    private String privateKey;

    @Column(columnDefinition = "TEXT")
    private String publicKey;

    @Column(columnDefinition = "TEXT")
    @Max(100)
    private String passwordHint = null;

    private boolean twoFactorEnabled = false;

    private String twoFactorVerificationCode = null;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChange = new Date();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Cipher> ciphers;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Password> passwords;

    public User(String email) {
        this.email = email;
    }
}


