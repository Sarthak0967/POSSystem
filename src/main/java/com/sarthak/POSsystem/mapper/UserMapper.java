package com.sarthak.POSsystem.mapper;

import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.UserDto;

import java.time.LocalDateTime;

public class UserMapper {
    public static UserDto toDTO(Users savedUser) {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setUsername(savedUser.getUsername());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogIn(savedUser.getLastLogIn());
        userDto.setPhone(savedUser.getPhone());

        return userDto;
    }

    public static Users toEntity(UserDto userDto) {
        Users createdUser = new Users();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setUsername(userDto.getUsername());
        createdUser.setRole(userDto.getRole());
        createdUser.setCreatedAt(userDto.getCreatedAt());
        createdUser.setUpdatedAt(LocalDateTime.now());
        createdUser.setLastLogIn(userDto.getLastLogIn());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setPassword(userDto.getPassword());

        return createdUser;
    }
}
