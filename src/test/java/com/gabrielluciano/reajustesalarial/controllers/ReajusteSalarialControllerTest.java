package com.gabrielluciano.reajustesalarial.controllers;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.exceptions.DuplicatedCpfException;
import com.gabrielluciano.reajustesalarial.services.ReajusteSalarialService;
import com.gabrielluciano.reajustesalarial.util.FuncionarioRequestCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReajusteSalarialControllerTest {

    @InjectMocks
    ReajusteSalarialController reajusteSalarialController;

    @Mock
    ReajusteSalarialService reajusteSalarialService;

    @Test
    void givenFuncionarioRequest_WhenCadastrarFuncionario_ThenReturn201AndId() {
        long expectedId = 1L;
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createValidFuncionarioRequest();
        when(reajusteSalarialService.cadastrarFuncionario(ArgumentMatchers.any()))
                .thenReturn(expectedId);

        ResponseEntity<Long> response = reajusteSalarialController.cadastrarFuncionario(funcionarioRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedId, response.getBody());
    }

    @Test
    void givenFuncionarioRequestWithDuplicatedCpf_WhenCadastrarFuncionario_ThenThrowException() {
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createValidFuncionarioRequest();
        when(reajusteSalarialService.cadastrarFuncionario(ArgumentMatchers.any()))
                .thenThrow(new DuplicatedCpfException(funcionarioRequest.getCpf()));

        assertThrows(DuplicatedCpfException.class, () ->
                reajusteSalarialController.cadastrarFuncionario(funcionarioRequest));
    }
}
