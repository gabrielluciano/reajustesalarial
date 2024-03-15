package com.gabrielluciano.reajustesalarial.dto;

import com.gabrielluciano.reajustesalarial.util.constraintvalidators.CpfConstraint;

public class ReajusteRequest {

    @CpfConstraint
    private String cpf;

    public ReajusteRequest() {
    }

    public ReajusteRequest(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
