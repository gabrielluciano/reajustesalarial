package com.gabrielluciano.reajustesalarial.exceptions;

public class DuplicatedCpfException extends RuntimeException {

    public DuplicatedCpfException(String cpf) {
        super(String.format("CPF '%s' já cadastrado", cpf));
    }
}
