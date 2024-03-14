package com.gabrielluciano.reajustesalarial.dto;

import com.gabrielluciano.reajustesalarial.models.Funcionario;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FuncionarioRequestMapperTest {

    @Test
    void givenFuncionarioRequest_ThenConvertsToFuncionario() {
        EnderecoRequest enderecoRequest = new EnderecoRequest();
        enderecoRequest.setCep("12345678912");
        enderecoRequest.setPais("Brasil");
        enderecoRequest.setEstado("Rio de Janeiro");
        enderecoRequest.setCidade("Rio de Janeiro");
        enderecoRequest.setLogradouro("Rua A, bairro B");
        enderecoRequest.setNumero("12A");
        enderecoRequest.setComplemento("Complemento");

        FuncionarioRequest funcionarioRequest = new FuncionarioRequest();
        funcionarioRequest.setCpf("12345678912");
        funcionarioRequest.setNome("Nome Funcionario");
        funcionarioRequest.setSalario(new BigDecimal("3000.01"));
        funcionarioRequest.setTelefone("5510999999999");
        funcionarioRequest.setDataNascimento(LocalDate.parse("1996-10-21"));
        funcionarioRequest.setEndereco(enderecoRequest);

        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(funcionarioRequest, Funcionario.class);

        assertEquals(funcionarioRequest.getCpf(), funcionario.getCpf());
        assertEquals(funcionarioRequest.getNome(), funcionario.getNome());
        assertEquals(funcionarioRequest.getSalario(), funcionario.getSalario());
        assertEquals(funcionarioRequest.getTelefone(), funcionario.getTelefone());
        assertEquals(funcionarioRequest.getDataNascimento(), funcionario.getDataNascimento());

        assertEquals(enderecoRequest.getCep(), funcionario.getEndereco().getCep());
        assertEquals(enderecoRequest.getPais(), funcionario.getEndereco().getPais());
        assertEquals(enderecoRequest.getEstado(), funcionario.getEndereco().getEstado());
        assertEquals(enderecoRequest.getCidade(), funcionario.getEndereco().getCidade());
        assertEquals(enderecoRequest.getLogradouro(), funcionario.getEndereco().getLogradouro());
        assertEquals(enderecoRequest.getNumero(), funcionario.getEndereco().getNumero());
        assertEquals(enderecoRequest.getComplemento(), funcionario.getEndereco().getComplemento());

        assertFalse(funcionario.isSalarioReajustado());
    }
}
