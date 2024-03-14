package com.gabrielluciano.reajustesalarial.services;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.exceptions.DuplicatedCpfException;
import com.gabrielluciano.reajustesalarial.models.Funcionario;
import com.gabrielluciano.reajustesalarial.repositories.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.gabrielluciano.reajustesalarial.util.FuncionarioCreator;
import com.gabrielluciano.reajustesalarial.util.FuncionarioRequestCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReajusteSalarialServiceImplTest {

    ReajusteSalarialServiceImpl reajusteSalarialService;

    @Mock
    FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUp() {
        reajusteSalarialService = new ReajusteSalarialServiceImpl(funcionarioRepository, new ModelMapper());
    }

    @Test
    void givenFuncionarioRequest_whenCadastrarFuncionario_ThenReturnId() {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createFromFuncionario(funcionario);
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        when(funcionarioRepository.save(ArgumentMatchers.any()))
                .thenReturn(funcionario);

        long id = reajusteSalarialService.cadastrarFuncionario(funcionarioRequest);

        assertEquals(funcionario.getId(), id);
    }

    @Test
    void givenFuncionarioRequestWithDuplicatedCpf_whenCadastrarFuncionario_thenThrowsException() {
        Funcionario funcionario = FuncionarioCreator.createValidFuncionario();
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createFromFuncionario(funcionario);
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(funcionario));

        assertThrows(DuplicatedCpfException.class, () -> reajusteSalarialService
                .cadastrarFuncionario(funcionarioRequest));
    }
}
