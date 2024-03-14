package com.gabrielluciano.reajustesalarial.util;

import com.gabrielluciano.reajustesalarial.dto.EnderecoRequest;
import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.models.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioRequestCreator {

    public static FuncionarioRequest createFromFuncionario(Funcionario funcionario) {
        EnderecoRequest enderecoRequest = new EnderecoRequest();
        enderecoRequest.setCep(funcionario.getEndereco().getCep());
        enderecoRequest.setPais(funcionario.getEndereco().getPais());
        enderecoRequest.setEstado(funcionario.getEndereco().getEstado());
        enderecoRequest.setCidade(funcionario.getEndereco().getCidade());
        enderecoRequest.setLogradouro(funcionario.getEndereco().getLogradouro());
        enderecoRequest.setNumero(funcionario.getEndereco().getNumero());
        enderecoRequest.setComplemento(funcionario.getEndereco().getComplemento());

        FuncionarioRequest funcionarioRequest = new FuncionarioRequest();
        funcionarioRequest.setCpf(funcionario.getCpf());
        funcionarioRequest.setNome(funcionario.getNome());
        funcionarioRequest.setSalario(funcionario.getSalario());
        funcionarioRequest.setTelefone(funcionario.getTelefone());
        funcionarioRequest.setDataNascimento(funcionario.getDataNascimento());
        funcionarioRequest.setEndereco(enderecoRequest);
        return funcionarioRequest;
    }

    public static FuncionarioRequest createValidFuncionarioRequest() {
        EnderecoRequest enderecoRequest = createValidEnderecoRequest();

        FuncionarioRequest funcionarioRequest = new FuncionarioRequest();
        funcionarioRequest.setCpf("551.583.240-00");
        funcionarioRequest.setNome("Nome Funcionario");
        funcionarioRequest.setSalario(new BigDecimal("3000.01"));
        funcionarioRequest.setTelefone("5510999998888");
        funcionarioRequest.setDataNascimento(LocalDate.parse("1996-10-21"));
        funcionarioRequest.setEndereco(enderecoRequest);
        return funcionarioRequest;
    }

    public static EnderecoRequest createValidEnderecoRequest() {
        EnderecoRequest enderecoRequest = new EnderecoRequest();
        enderecoRequest.setCep("12345678912");
        enderecoRequest.setPais("Brasil");
        enderecoRequest.setEstado("Rio de Janeiro");
        enderecoRequest.setCidade("Rio de Janeiro");
        enderecoRequest.setLogradouro("Rua A, bairro B");
        enderecoRequest.setNumero("12A");
        enderecoRequest.setComplemento("Complemento");
        return enderecoRequest;
    }
}
