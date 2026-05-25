package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.mapper.UserMapper;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.UserDto;
import com.sarthak.POSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        Users user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(
                UserMapper.toDTO(user)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws UserException {
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(
                UserMapper.toDTO(user)
        );
    }
}
