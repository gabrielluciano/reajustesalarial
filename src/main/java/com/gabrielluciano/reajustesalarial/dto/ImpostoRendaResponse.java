package com.gabrielluciano.reajustesalarial.dto;

public class ImpostoRendaResponse {

    private String cpf;
    private String imposto;

    public ImpostoRendaResponse() {
    }

    public ImpostoRendaResponse(String cpf, String imposto) {
        this.cpf = cpf;
        this.imposto = imposto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getImposto() {
        return imposto;
    }

    public void setImposto(String imposto) {
        this.imposto = imposto;
    }
}
