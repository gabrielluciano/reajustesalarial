package com.gabrielluciano.reajustesalarial.services;

import com.gabrielluciano.reajustesalarial.dto.FuncionarioRequest;

public interface ReajusteSalarialService {

    /**
     * Cadastra um novo funcionario
     *
     * @param funcionarioRequest DTO contendo informações do funcionário a ser cadastrado
     * @return id do funcionário criado
     */
    long cadastrarFuncionario(FuncionarioRequest funcionarioRequest);
}
