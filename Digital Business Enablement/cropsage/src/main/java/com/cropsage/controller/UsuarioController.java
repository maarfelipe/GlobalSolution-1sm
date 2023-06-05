package com.cropsage.controller;

import com.cropsage.model.Produto;
import com.cropsage.model.ProdutoLabel;
import com.cropsage.model.Solo;
import com.cropsage.model.Usuario;
import com.cropsage.repository.ProdutoRepository;
import com.cropsage.repository.SoloRepository;
import com.cropsage.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
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

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Usuario usuario) {
        log.info("cadastrando usuario");
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
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

    @GetMapping("{id}/testsolo")
    public ResponseEntity<Object> testSolo(@PathVariable Long id) {
        String url = "http://127.0.0.1:5000/46/76/77/18.2356751/19.68538502/6.967843048/83.74879344";
        WebClient.Builder builder = WebClient.builder();

        String result = builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info(result);

        return ResponseEntity.ok(result);
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

        Produto produto = Produto.builder().nome(result.getLabel()).build();

        solo.setProduto(produto);

        produtoRepository.save(produto);
        soloRepository.save(solo);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(solo);
    }

}
