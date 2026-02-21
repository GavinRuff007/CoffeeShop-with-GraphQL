package org.example.coffeestore.security.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponse {
    private String token;
    private String role;
    private LocalDateTime time;

}
