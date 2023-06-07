package com.cropsage.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cropsage.model.Credencial;
import com.cropsage.model.Token;
import com.cropsage.model.Usuario;
import com.cropsage.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Token generateToken(Credencial credencial) {
        Algorithm alg = Algorithm.HMAC256("meusecret");
        var jwt = JWT.create()
                .withSubject(credencial.email())
                .withIssuer("CropSage")
                .withExpiresAt(Instant.now().plus(4, ChronoUnit.HOURS))
                .sign(alg);
        return new Token(jwt, "JWT", "Bearer");
    }

    public Usuario validate(String token) {
        Algorithm alg = Algorithm.HMAC256("meusecret");
        var email = JWT.require(alg)
                .withIssuer("CropSage")
                .build()
                .verify(token)
                .getSubject();

        return (Usuario) usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new JWTVerificationException("usuario não encontrado"));
    }

    public String getToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        return header.replace("Bearer ", "");
    }

}
