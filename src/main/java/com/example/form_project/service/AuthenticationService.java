package com.example.form_project.service;

import com.example.form_project.exception.UserAlreadyExistsException;
import com.example.form_project.model.User;
import com.example.form_project.repository.UserRepository;
import com.example.form_project.security.AuthenticationResponse;
import com.example.form_project.dto.LoginRequest;
import com.example.form_project.dto.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        if (emailExists(request.email()))
            throw new UserAlreadyExistsException("User already exists with Email: " + request.email());

        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user = repository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public boolean emailExists(String email) {
        Optional<User> user = repository.findByEmail(email);
        return user.isPresent();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        if (!emailExists(request.email()))
            throw new UsernameNotFoundException("User not exist");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = repository.findByEmail(request.email()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
