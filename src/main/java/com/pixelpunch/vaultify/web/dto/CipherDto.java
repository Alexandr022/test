package com.pixelpunch.vaultify.web.dto;

import com.pixelpunch.vaultify.core.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CipherDto {
    private Long id;
    private User owner;
    @Column(columnDefinition = "TEXT")
    private String data;
    private boolean favorite = false;
    private Date created;
    private Date lastModified;
}
