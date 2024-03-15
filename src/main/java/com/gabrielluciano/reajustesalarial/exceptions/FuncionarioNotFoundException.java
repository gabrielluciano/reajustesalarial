package com.gabrielluciano.reajustesalarial.exceptions;

public class FuncionarioNotFoundException extends RuntimeException {
    public FuncionarioNotFoundException(String cpf) {
        super(String.format("Funcionário com cpf '%s' não encontrado", cpf));
    }
}
