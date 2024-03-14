package com.gabrielluciano.reajustesalarial.util;

import com.gabrielluciano.reajustesalarial.models.Endereco;
import com.gabrielluciano.reajustesalarial.models.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioCreator {

    public static Funcionario createValidFuncionario() {
        Endereco endereco = EnderecoCreator.createValidEndereco();

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setCpf("551.583.240-00");
        funcionario.setNome("Nome Funcionario");
        funcionario.setSalario(new BigDecimal("3000.01"));
        funcionario.setTelefone("5510999998888");
        funcionario.setSalarioReajustado(false);
        funcionario.setDataNascimento(LocalDate.parse("1996-10-21"));
        funcionario.setEndereco(endereco);
        return funcionario;
    }
}
