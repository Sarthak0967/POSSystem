package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.configuration.JwtProvider;
import com.sarthak.POSsystem.domain.UserRole;
import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.mapper.UserMapper;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.UserDto;
import com.sarthak.POSsystem.payload.response.AuthResponse;
import com.sarthak.POSsystem.repository.UserRepository;
import com.sarthak.POSsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.userdetails.UserDetailsMapFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;



    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {

        Users user = userRepository.findByEmail(userDto.getEmail());

        if(user != null){
            throw new UserException("email id already registered.");
        }

        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new UserException("Role admin is not allowed");
        }

        Users newUser = new Users();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setUsername(userDto.getUsername());

        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setLastLogIn(LocalDateTime.now());

        Users savedUser = userRepository.save(newUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDto.getId(), userDto.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJWTToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setUserDto(UserMapper.toDTO(savedUser));



        return authResponse;
    }

    @Override
    public AuthResponse login(UserDto userDto) throws UserException {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.iterator().next().getAuthority();

        String jwt = jwtProvider
                .generateJWTToken(authentication);

        Users user = userRepository.findByEmail(email);
        user.setLastLogIn(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully");
        authResponse.setUserDto(UserMapper.toDTO(user));

        return authResponse;
    }

    private Authentication authenticate(String email , String password) throws UserException {

        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);

        if( userDetails == null) {

                throw new UserException("Email id doesn't exist. "+email);

        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new UserException("Password not exist");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
