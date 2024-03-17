package com.gabrielluciano.reajustesalarial.controllers;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.dto.ImpostoRendaResponse;
import com.gabrielluciano.reajustesalarial.dto.ReajusteRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteResponse;
import com.gabrielluciano.reajustesalarial.services.ReajusteSalarialService;
import com.gabrielluciano.reajustesalarial.util.constraintvalidators.CpfConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<ReajusteResponse> calcularReajuste(@Valid @RequestBody ReajusteRequest reajusteRequest) {
        return ResponseEntity.ok(service.calcularReajuste(reajusteRequest));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ImpostoRendaResponse> calcularImpostoRenda(@PathVariable @CpfConstraint String cpf) {
        return ResponseEntity.ok(service.calcularImpostoRenda(cpf));
    }
}
