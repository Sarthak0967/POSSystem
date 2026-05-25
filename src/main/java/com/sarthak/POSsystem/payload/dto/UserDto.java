package com.sarthak.POSsystem.payload.dto;

import com.sarthak.POSsystem.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {


    private Long id;

    private String username;

    private String email;

    private String phone;

    private UserRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogIn;

    private String password;

}
