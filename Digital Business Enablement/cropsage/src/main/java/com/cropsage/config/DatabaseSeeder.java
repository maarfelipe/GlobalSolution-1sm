package com.cropsage.config;

import com.cropsage.model.Solo;
import com.cropsage.model.Usuario;
import com.cropsage.repository.SoloRepository;
import com.cropsage.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    SoloRepository soloRepository;

    @Override
    public void run(String... args) throws Exception {

        Usuario u1 = Usuario.builder().nome("nome1").email("email1").senha("senha1").build();
        Usuario u2 = Usuario.builder().nome("nome2").email("email2").senha("senha2").build();
        Usuario u3 = Usuario.builder().nome("nome3").email("email3").senha("senha3").build();

        usuarioRepository.saveAll(List.of(
                u1,u2,u3
        ));
        
        soloRepository.saveAll(List.of(
                Solo.builder().nitrogenio(1).potassio(1).fosforo(1)
                        .temperatura(1).umidade(1).ph(1).chuva(1)
                        .usuario(u1).build(),
                Solo.builder().nitrogenio(2).potassio(2).fosforo(2)
                        .temperatura(2).umidade(2).ph(2).chuva(2)
                        .usuario(u2).build(),
                Solo.builder().nitrogenio(3).potassio(3).fosforo(3)
                        .temperatura(3).umidade(3).ph(3).chuva(3)
                        .usuario(u3).build()
        ));

    }

}
