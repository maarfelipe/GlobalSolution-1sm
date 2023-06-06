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

    @GetMapping
    public Page<Usuario> index(@PageableDefault(size = 5) Pageable pageable){
        return usuarioRepository.findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> index(@PathVariable Long id) {
        log.info("buscando usuario "+id);
        var result = usuarioRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não Encontrado"));
        return ResponseEntity.ok(result);
    }

    @PostMapping("cadastrar")
    public ResponseEntity<Object> cadastro(@RequestBody @Valid Usuario usuario) {
        log.info("cadastrando usuario");
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody Credencial credencial) {
        manager.authenticate(credencial.toAuthentication());
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("buscando usuario "+id);
        var result = usuarioRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não Encontrado"));
        soloRepository.deleteAll(soloRepository.findByUsuario(result));
        usuarioRepository.delete(result);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        log.info("buscando usuario "+id);
        usuarioRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não Encontrado"));
        usuario.setId(id);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("{id}/solo")
    public ResponseEntity<Object> addSolo(@PathVariable Long id, @RequestBody @Valid Solo solo) {
        log.info("buscando usuario "+id);
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não Encontrado"));
        usuario.getSoloList().add(solo);

        String url = "http://localhost:5000/"
                + String.valueOf(solo.getNitrogenio()) + "/"
                + String.valueOf(solo.getFosforo()) + "/"
                + String.valueOf(solo.getPotassio()) + "/"
                + String.valueOf(solo.getTemperatura()) + "/"
                + String.valueOf(solo.getUmidade()) + "/"
                + String.valueOf(solo.getPh()) + "/"
                + String.valueOf(solo.getChuva());

        log.info("fazendo requisição na url: " + url);

        WebClient.Builder builder = WebClient.builder();

        ProdutoLabel result = builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ProdutoLabel.class)
                .block();

        log.info(result.getLabel());

        Produto produto = Produto.builder().nome(result.getLabel()).epoca(result.getEpoca()).build();

        solo.setProduto(produto);

        produtoRepository.save(produto);
        soloRepository.save(solo);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(solo);
    }



}
