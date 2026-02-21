package org.example.coffeestore.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.coffeestore.security.dto.LoginResponse;
import org.example.coffeestore.security.entity.AppUser;
import org.example.coffeestore.security.service.JwtService;
import org.example.coffeestore.security.dto.LoginRequest;
import org.example.coffeestore.security.repository.AppUserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserRepository userRepo;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {

        LoginResponse loginResponse = new LoginResponse();

        AppUser user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        loginResponse.setToken(jwtService.generateToken(user.getUsername(), user.getRole()));
        loginResponse.setRole(user.getRole());
        loginResponse.setTime(LocalDateTime.now());

        return loginResponse;
    }
}
