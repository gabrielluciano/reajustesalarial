package com.gabrielluciano.reajustesalarial.controllers;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteResponse;
import com.gabrielluciano.reajustesalarial.exceptions.DuplicatedCpfException;
import com.gabrielluciano.reajustesalarial.exceptions.FuncionarioNotFoundException;
import com.gabrielluciano.reajustesalarial.exceptions.SalarioAlreadyReajustadoException;
import com.gabrielluciano.reajustesalarial.services.ReajusteSalarialService;
import com.gabrielluciano.reajustesalarial.util.FuncionarioRequestCreator;
import com.gabrielluciano.reajustesalarial.util.ReajusteResponseCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReajusteSalarialControllerTest {

    @InjectMocks
    ReajusteSalarialController reajusteSalarialController;

    @Mock
    ReajusteSalarialService reajusteSalarialService;

    @Test
    void givenFuncionarioRequest_WhenCadastrarFuncionario_thenReturn201AndId() {
        long expectedId = 1L;
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createValidFuncionarioRequest();
        when(reajusteSalarialService.cadastrarFuncionario(ArgumentMatchers.any()))
                .thenReturn(expectedId);

        ResponseEntity<Long> response = reajusteSalarialController.cadastrarFuncionario(funcionarioRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
    }

    @Test
    void givenFuncionarioRequestWithDuplicatedCpf_WhenCadastrarFuncionario_thenThrowException() {
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createValidFuncionarioRequest();
        when(reajusteSalarialService.cadastrarFuncionario(ArgumentMatchers.any()))
                .thenThrow(new DuplicatedCpfException(funcionarioRequest.getCpf()));

        assertThrows(DuplicatedCpfException.class, () ->
                reajusteSalarialController.cadastrarFuncionario(funcionarioRequest));
    }

    @Test
    void givenReajusteRequest_WhenCalcularReajuste_thenReturn200AndReajusteResponse() {
        ReajusteResponse reajusteResponse = ReajusteResponseCreator.createValidReajusteResponse();
        when(reajusteSalarialService.calcularReajuste(ArgumentMatchers.any()))
                .thenReturn(reajusteResponse);

        ResponseEntity<ReajusteResponse> response = reajusteSalarialController
                .calcularReajuste(new ReajusteRequest(reajusteResponse.getCpf()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reajusteResponse.getCpf(), response.getBody().getCpf());
        assertEquals(reajusteResponse.getValorReajuste(), response.getBody().getValorReajuste());
    }

    @Test
    void givenReajusteRequestWithNotFoundCpf_WhenCalcularReajuste_thenThrowsException() {
        ReajusteResponse reajusteResponse = ReajusteResponseCreator.createValidReajusteResponse();
        when(reajusteSalarialService.calcularReajuste(ArgumentMatchers.any()))
                .thenThrow(new FuncionarioNotFoundException(reajusteResponse.getCpf()));

        assertThrows(FuncionarioNotFoundException.class, () ->
                reajusteSalarialController.calcularReajuste(new ReajusteRequest(reajusteResponse.getCpf())));
    }

    @Test
    void givenReajusteRequestWithSalarioReajustado_WhenCalcularReajuste_thenThrowsException() {
        ReajusteResponse reajusteResponse = ReajusteResponseCreator.createValidReajusteResponse();
        when(reajusteSalarialService.calcularReajuste(ArgumentMatchers.any()))
                .thenThrow(new SalarioAlreadyReajustadoException(reajusteResponse.getCpf()));

        assertThrows(SalarioAlreadyReajustadoException.class, () ->
                reajusteSalarialController.calcularReajuste(new ReajusteRequest(reajusteResponse.getCpf())));
    }
}
