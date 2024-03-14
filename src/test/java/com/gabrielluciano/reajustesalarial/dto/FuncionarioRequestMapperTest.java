package com.gabrielluciano.reajustesalarial.dto;

import com.gabrielluciano.reajustesalarial.models.Funcionario;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import com.gabrielluciano.reajustesalarial.util.FuncionarioRequestCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FuncionarioRequestMapperTest {

    @Test
    void givenFuncionarioRequest_ThenConvertsToFuncionario() {
        FuncionarioRequest funcionarioRequest = FuncionarioRequestCreator.createValidFuncionarioRequest();

        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(funcionarioRequest, Funcionario.class);

        assertEquals(funcionarioRequest.getCpf(), funcionario.getCpf());
        assertEquals(funcionarioRequest.getNome(), funcionario.getNome());
        assertEquals(funcionarioRequest.getSalario(), funcionario.getSalario());
        assertEquals(funcionarioRequest.getTelefone(), funcionario.getTelefone());
        assertEquals(funcionarioRequest.getDataNascimento(), funcionario.getDataNascimento());

        assertEquals(funcionarioRequest.getEndereco().getCep(), funcionario.getEndereco().getCep());
        assertEquals(funcionarioRequest.getEndereco().getPais(), funcionario.getEndereco().getPais());
        assertEquals(funcionarioRequest.getEndereco().getEstado(), funcionario.getEndereco().getEstado());
        assertEquals(funcionarioRequest.getEndereco().getCidade(), funcionario.getEndereco().getCidade());
        assertEquals(funcionarioRequest.getEndereco().getLogradouro(), funcionario.getEndereco().getLogradouro());
        assertEquals(funcionarioRequest.getEndereco().getNumero(), funcionario.getEndereco().getNumero());
        assertEquals(funcionarioRequest.getEndereco().getComplemento(), funcionario.getEndereco().getComplemento());

        assertFalse(funcionario.isSalarioReajustado());
    }
}
