package com.example.levelupgamer.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email; // Cambia esto de 'username' a 'email' si es necesario
    private String password;
}
