package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.models.Users;

import java.util.List;

public interface UserService {
    Users getUserFromJwtToken(String token) throws UserException;
    Users getCurrentUser() throws UserException;
    Users getUserByEmail(String email) throws UserException;
    Users getUserById(Long id) throws UserException;
    List<Users> getAllUsers();
}
