package com.gabrielluciano.reajustesalarial.services;

import com.gabrielluciano.reajustesalarial.dto.EnderecoRequest;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReajusteSalarialServiceImplTest {

    ReajusteSalarialServiceImpl reajusteSalarialService;

    @Mock
    FuncionarioRepository funcionarioRepository;

    Funcionario funcionario;
    FuncionarioRequest funcionarioRequest;

    @BeforeEach
    void setUp() {
        reajusteSalarialService = new ReajusteSalarialServiceImpl(funcionarioRepository, new ModelMapper());

        EnderecoRequest er = new EnderecoRequest();
        er.setCep("12345678912");
        er.setPais("Brasil");
        er.setEstado("Rio de Janeiro");
        er.setCidade("Rio de Janeiro");
        er.setLogradouro("Rua A, bairro B");
        er.setNumero("12A");
        er.setComplemento("Complemento");

        FuncionarioRequest fr = new FuncionarioRequest();
        fr.setCpf("12345678912");
        fr.setNome("Nome Funcionario");
        fr.setSalario(new BigDecimal("3000.01"));
        fr.setTelefone("5510999999999");
        fr.setDataNascimento(LocalDate.parse("1996-10-21"));
        fr.setEndereco(er);

        funcionarioRequest = fr;

        ModelMapper modelMapper = new ModelMapper();
        funcionario = modelMapper.map(funcionarioRequest, Funcionario.class);
        funcionario.setId(1L);
        funcionario.getEndereco().setId(1L);
    }

    @Test
    void givenFuncionarioRequest_whenCadastrarFuncionario_ThenReturnId() {
        long expectedId = funcionario.getId();
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        when(funcionarioRepository.save(ArgumentMatchers.any()))
                .thenReturn(funcionario);

        long id = reajusteSalarialService.cadastrarFuncionario(funcionarioRequest);

        assertEquals(expectedId, id);
    }

    @Test
    void givenFuncionarioRequestWithDuplicatedCpf_whenCadastrarFuncionario_thenThrowsException() {
        when(funcionarioRepository.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(funcionario));

        assertThrows(DuplicatedCpfException.class, () -> reajusteSalarialService
                .cadastrarFuncionario(funcionarioRequest));
    }
}
