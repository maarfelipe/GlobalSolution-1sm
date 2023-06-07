package com.cropsage.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public record Credencial(String email, String senha) {
    public Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
