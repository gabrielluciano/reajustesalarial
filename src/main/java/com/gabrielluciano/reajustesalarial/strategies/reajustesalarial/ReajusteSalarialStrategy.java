package com.gabrielluciano.reajustesalarial.strategies.reajustesalarial;

import java.math.BigDecimal;

public interface ReajusteSalarialStrategy {

    String getPercentualReajuste();
    BigDecimal getNovoSalario(BigDecimal salarioAtual);
    BigDecimal getValorReajuste(BigDecimal salarioAtual);
}
