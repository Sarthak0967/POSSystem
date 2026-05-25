package com.sarthak.POSsystem.payload.response;

import com.sarthak.POSsystem.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private UserDto userDto;
}
