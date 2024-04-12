package com.pixelpunch.vaultify.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "histroty_user_passwords")
@NoArgsConstructor
@AllArgsConstructor
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int length;
    private boolean includeUppercases = false;
    private boolean includeNumbers = false;
    private boolean includeSpecials = false;
    @Temporal(TemporalType.TIMESTAMP)
    private Date generatedTime = new Date();
    private String passwords;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
