package com.gabrielluciano.reajustesalarial.dto;

import java.math.BigDecimal;

public class ReajusteResponse {

    private String cpf;
    private BigDecimal novoSalario;
    private BigDecimal valorReajuste;
    private String percentualReajuste;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getNovoSalario() {
        return novoSalario;
    }

    public void setNovoSalario(BigDecimal novoSalario) {
        this.novoSalario = novoSalario;
    }

    public BigDecimal getValorReajuste() {
        return valorReajuste;
    }

    public void setValorReajuste(BigDecimal valorReajuste) {
        this.valorReajuste = valorReajuste;
    }

    public String getPercentualReajuste() {
        return percentualReajuste;
    }

    public void setPercentualReajuste(String percentualReajuste) {
        this.percentualReajuste = percentualReajuste;
    }
}
