package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.payload.dto.UserDto;
import com.sarthak.POSsystem.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;
}
