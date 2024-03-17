package com.gabrielluciano.reajustesalarial.strategies.reajustesalarial;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BaixoRangeSalarialReajusteStrategy implements ReajusteSalarialStrategy {

    private static final double percentualReajuste = 15;

    @Override
    public String getPercentualReajuste() {
        return String.format("%d%%", (int) percentualReajuste);
    }

    @Override
    public BigDecimal getNovoSalario(BigDecimal salarioAtual) {
        return salarioAtual.add(getValorReajuste(salarioAtual))
                .setScale(2, RoundingMode.FLOOR);
    }

    @Override
    public BigDecimal getValorReajuste(BigDecimal salarioAtual) {
        return salarioAtual.multiply(BigDecimal.valueOf(percentualReajuste / 100.0))
                .setScale(2, RoundingMode.FLOOR);
    }
}
