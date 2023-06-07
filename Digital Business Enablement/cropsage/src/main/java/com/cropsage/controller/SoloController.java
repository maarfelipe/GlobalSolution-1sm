package com.cropsage.controller;

import com.cropsage.model.Localizacao;
import com.cropsage.model.Produto;
import com.cropsage.model.ProdutoLabel;
import com.cropsage.model.Solo;
import com.cropsage.repository.LocalizacaoRepository;
import com.cropsage.repository.ProdutoRepository;
import com.cropsage.repository.SoloRepository;
import com.cropsage.repository.UsuarioRepository;
import com.cropsage.service.TokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cropsage/api/solo")
public class SoloController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    SoloRepository soloRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    LocalizacaoRepository localizacaoRepository;

    @Autowired
    TokenService tokenService;

    @Value("${cropsage.ml.host}")
    private String mlhost;
    @Value("${cropsage.ml.port}")
    private String mlport;

    @PostMapping
    public ResponseEntity<Object> addSolo(@RequestHeader("Authorization") String header, @RequestBody @Valid Solo solo) {
        log.info("buscando usuario");
        var usuario = tokenService.validate(tokenService.getToken(header));

        String url = mlhost + ":" + mlport + "/"
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
        solo.setUsuario(usuario);
        solo.getLocalizacao().setUsuario(usuario);

        localizacaoRepository.save(solo.getLocalizacao());
        produtoRepository.save(produto);
        soloRepository.save(solo);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(solo);
    }

    @GetMapping
    public Page<Solo> solos(@RequestHeader("Authorization") String header, @PageableDefault(size = 5) Pageable pageable) {
        log.info("buscando usuario");
        var usuario = tokenService.validate(tokenService.getToken(header));
        var listSolo = soloRepository.findByUsuario(usuario);
        int start = (int) pageable.getOffset();
        int end = (int) (Math.min((start + pageable.getPageSize()), listSolo.size()));
        return new PageImpl<Solo>(listSolo.subList(start, end), pageable, listSolo.size());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> index(@RequestHeader("Authorization") String header, @PathVariable long id) {
        log.info("buscando usuario");
        var usuario = tokenService.validate(tokenService.getToken(header));
        var solo = soloRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Solo não encontrado"));
        if (solo.getUsuario() != usuario) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Esse solo é de outro Usuário");
        }
        return ResponseEntity.ok(solo);
    }

    @GetMapping("produto/{nome}")
    public Page<Solo> solosByProdutoNome(@RequestHeader("Authorization") String header, @PageableDefault(size = 5) Pageable pageable, @PathVariable String nome) {
        log.info("buscando usuario");
        var usuario = tokenService.validate(tokenService.getToken(header));
        var listSolo = soloRepository.findByUsuarioAndProduto_NomeContainsIgnoreCase(usuario, nome);
        int start = (int) pageable.getOffset();
        int end = (int) (Math.min((start + pageable.getPageSize()), listSolo.size()));
        return new PageImpl<Solo>(listSolo.subList(start, end), pageable, listSolo.size());
    }

    @GetMapping("localizacao/{nome}")
    public Page<Solo> solosByLocalizacaoNome(@RequestHeader("Authorization") String header, @PageableDefault(size = 5) Pageable pageable, @PathVariable String nome) {
        log.info("buscando usuario");
        var usuario = tokenService.validate(tokenService.getToken(header));
        var listSolo = soloRepository.findByUsuarioAndLocalizacao_NomeContainsIgnoreCase(usuario, nome);
        int start = (int) pageable.getOffset();
        int end = (int) (Math.min((start + pageable.getPageSize()), listSolo.size()));
        return new PageImpl<Solo>(listSolo.subList(start, end), pageable, listSolo.size());
    }

}
