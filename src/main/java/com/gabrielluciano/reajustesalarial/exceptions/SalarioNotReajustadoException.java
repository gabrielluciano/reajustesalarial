package com.gabrielluciano.reajustesalarial.exceptions;

public class SalarioNotReajustadoException extends RuntimeException {
    public SalarioNotReajustadoException(String cpf) {
        super(String.format("Salário do usuário de cpf '%s' ainda não foi reajustado. Aplique o reajuste para calcular o IR", cpf));
    }
}
