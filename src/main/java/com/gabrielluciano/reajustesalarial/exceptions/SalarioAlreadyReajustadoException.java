package com.gabrielluciano.reajustesalarial.exceptions;

public class SalarioAlreadyReajustadoException extends RuntimeException {
    public SalarioAlreadyReajustadoException(String cpf) {
        super(String.format("Salário do usuário de cpf '%s' já foi reajustado", cpf));
    }
}
