package com.cropsage.controller;

import com.cropsage.model.*;
import com.cropsage.repository.ProdutoRepository;
import com.cropsage.repository.SoloRepository;
import com.cropsage.repository.UsuarioRepository;
import com.cropsage.service.TokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/cropsage/api/usuario")
public class UsuarioController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SoloRepository soloRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<Object> index(@RequestHeader("Authorization") String header) {
        log.info("buscando usuario");
        var result = tokenService.validate(tokenService.getToken(header));
        return ResponseEntity.ok(result);
    }

    @CrossOrigin
    @PostMapping("cadastrar")
    public ResponseEntity<Object> cadastro(@RequestBody @Valid Usuario usuario) {
        log.info("cadastrando usuario");
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody Credencial credencial) {
        manager.authenticate(credencial.toAuthentication());
        log.info("autenticado");
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String header) {
        log.info("buscando usuario");
        var result = tokenService.validate(tokenService.getToken(header));
        log.info("deletando solos do usuario");
        soloRepository.deleteAll(result.getSoloList());
        log.info("deletando usuario");
        usuarioRepository.delete(result);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String header, @RequestBody @Valid Usuario usuario) {
        log.info("buscando usuario");
        var result = tokenService.validate(tokenService.getToken(header));
        usuario.setId(result.getId());
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        log.info("salvando usuario");
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

//    @PostMapping("solo")
//    public ResponseEntity<Object> addSolo(@RequestHeader("Authorization") String header, @RequestBody @Valid Solo solo) {
//        log.info("buscando usuario");
//        var usuario = tokenService.validate(tokenService.getToken(header));
//
//        String url = "http://localhost:5000/"
//                + String.valueOf(solo.getNitrogenio()) + "/"
//                + String.valueOf(solo.getFosforo()) + "/"
//                + String.valueOf(solo.getPotassio()) + "/"
//                + String.valueOf(solo.getTemperatura()) + "/"
//                + String.valueOf(solo.getUmidade()) + "/"
//                + String.valueOf(solo.getPh()) + "/"
//                + String.valueOf(solo.getChuva());
//
//        log.info("fazendo requisição na url: " + url);
//
//        WebClient.Builder builder = WebClient.builder();
//
//        ProdutoLabel result = builder.build()
//                .get()
//                .uri(url)
//                .retrieve()
//                .bodyToMono(ProdutoLabel.class)
//                .block();
//
//        log.info(result.getLabel());
//
//        Produto produto = Produto.builder().nome(result.getLabel()).epoca(result.getEpoca()).build();
//
//        solo.setProduto(produto);
//        solo.setUsuario(usuario);
//
//        produtoRepository.save(produto);
//        soloRepository.save(solo);
//        usuarioRepository.save(usuario);
//        return ResponseEntity.ok(solo);
//    }
//
//    @GetMapping("solo")
//    public Page<Solo> solos(@RequestHeader("Authorization") String header, @PageableDefault(size = 5) Pageable pageable) {
//        log.info("buscando usuario");
//        var usuario = tokenService.validate(tokenService.getToken(header));
//        var listSolo = usuario.getSoloList();
//        int start = (int) pageable.getOffset();
//        int end = (int) (Math.min((start + pageable.getPageSize()), listSolo.size()));
//        return new PageImpl<Solo>(listSolo.subList(start, end), pageable, listSolo.size());
//    }


}
