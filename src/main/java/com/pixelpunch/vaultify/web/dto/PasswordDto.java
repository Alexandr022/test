package com.pixelpunch.vaultify.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixelpunch.vaultify.core.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    private Long id;
    private String password;
    private int length;
    private boolean includeUppercase;
    private boolean includeNumbers;
    private boolean includeSpecial;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date generatedTime;
    @JsonIgnore
    private User user;

    public PasswordDto(String password, int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial) {
        this.password = password;
        this.length = length;
        this.includeUppercase = includeUppercase;
        this.includeNumbers = includeNumbers;
        this.includeSpecial = includeSpecial;
    }
}

