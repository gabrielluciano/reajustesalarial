package com.gabrielluciano.reajustesalarial.services;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;
import com.gabrielluciano.reajustesalarial.dto.ImpostoRendaResponse;
import com.gabrielluciano.reajustesalarial.dto.ReajusteRequest;
import com.gabrielluciano.reajustesalarial.dto.ReajusteResponse;

public interface ReajusteSalarialService {

    /**
     * Cadastra um novo funcionario
     *
     * @param funcionarioRequest DTO contendo informações do funcionário a ser cadastrado
     * @return id do funcionário criado
     */
    long cadastrarFuncionario(FuncionarioRequest funcionarioRequest);

    /**
     * Calcula o reajuste salarial de um funcionário
     *
     * @param reajusteRequest DTO contendo o cpf do funcionário a ter o salário reajustado
     * @return ReajusteResponse contendo o cpf, percentual de reajuste, valor de reajuste e novo salário do funcionário
     */
    ReajusteResponse calcularReajuste(ReajusteRequest reajusteRequest);

    /**
     * Calcula o imposto de renda para um funcionário que já recebeu reajuste
     *
     * @param cpf CPF do funcionário
     * @return ImpostoRendaResponse contento o cpf e o valor do imposto de renda a ser pago pelo funcionário
     */
    ImpostoRendaResponse calcularImpostoRenda(String cpf);
}
