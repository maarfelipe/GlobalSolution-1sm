package com.cropsage.config;

import com.cropsage.model.Usuario;
import com.cropsage.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {

        Usuario u1 = Usuario.builder().nome("nome1").email("email1@gmail.com").senha(encoder.encode("senha1")).build();
        Usuario u2 = Usuario.builder().nome("nome2").email("email2@gmail.com").senha(encoder.encode("senha2")).build();
        Usuario u3 = Usuario.builder().nome("nome3").email("email3@gmail.com").senha(encoder.encode("senha3")).build();

        usuarioRepository.saveAll(List.of(
                u1,u2,u3
        ));

    }

}
