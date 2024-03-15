package com.gabrielluciano.reajustesalarial.controllers;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.services.ReajusteSalarialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reajustesalarial")
public class ReajusteSalarialController {

    private final ReajusteSalarialService service;

    public ReajusteSalarialController(ReajusteSalarialService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> cadastrarFuncionario(@Valid @RequestBody FuncionarioRequest funcionarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarFuncionario(funcionarioRequest));
    }
}
