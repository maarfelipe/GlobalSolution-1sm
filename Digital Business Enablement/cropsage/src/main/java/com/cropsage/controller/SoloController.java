package com.cropsage.controller;

import com.cropsage.model.Solo;
import com.cropsage.repository.SoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cropsage/api/solo")
public class SoloController {

    @Autowired
    SoloRepository soloRepository;

    @GetMapping
    public Page<Solo> index(@PageableDefault(size = 5) Pageable pageable){
        return soloRepository.findAll(pageable);
    }

}
