package com.gabrielluciano.reajustesalarial.services.reajuste.strategies;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class BaseRangeSalarialReajusteStrategy implements ReajusteSalarialStrategy {

    protected final BigDecimal taxa;

    public BaseRangeSalarialReajusteStrategy() {
        this.taxa = getTaxa();
    }

    protected abstract BigDecimal getTaxa();

    @Override
    public String getPercentualReajuste() {
        return String.format("%s%%", taxa.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.FLOOR));
    }

    @Override
    public BigDecimal getNovoSalario(BigDecimal salarioAtual) {
        return salarioAtual.add(getValorReajuste(salarioAtual))
                .setScale(2, RoundingMode.FLOOR);
    }

    @Override
    public BigDecimal getValorReajuste(BigDecimal salarioAtual) {
        return salarioAtual.multiply(taxa).setScale(2, RoundingMode.FLOOR);
    }
}
