package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.configuration.JwtProvider;
import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.repository.UserRepository;
import com.sarthak.POSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public Users getUserFromJwtToken(String token) throws UserException {

        String email = jwtProvider.getEmailFromToken(token);
        Users user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("Invalid User");
        }
        return user;
    }

    @Override
    public Users getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Users user = userRepository.findByEmail(email);

        if(user == null ){
            throw new UserException("USer not found");
        }
        return user;
    }

    @Override
    public Users getUserByEmail(String email) throws UserException {

        Users user = userRepository.findByEmail(email);

        if(user == null ){
            throw new UserException("USer not found");
        }
        return user;
    }

    @Override
    public Users getUserById(Long id) throws UserException {
        return userRepository.findById(id).orElseThrow(
                () -> new UserException("User not found")
        );
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
}
