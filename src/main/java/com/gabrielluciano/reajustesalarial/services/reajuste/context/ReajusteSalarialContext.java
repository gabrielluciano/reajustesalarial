package com.gabrielluciano.reajustesalarial.services.reajuste.context;

import com.gabrielluciano.reajustesalarial.services.reajuste.strategies.*;

import java.math.BigDecimal;

import static com.gabrielluciano.reajustesalarial.util.bigdecimal.BigDecimalComparison.lessOrEqualThan;

public class ReajusteSalarialContext implements ReajusteSalarialStrategy {

    private ReajusteSalarialStrategy strategy;

    private ReajusteSalarialContext(ReajusteSalarialStrategy strategy) {
        this.strategy = strategy;
    }

    public static ReajusteSalarialContext fromSalario(BigDecimal salario) {
        ReajusteSalarialStrategy strategy;
        if (lessOrEqualThan(salario, new BigDecimal("400.00"))) {
            strategy = new BaixoRangeSalarialReajusteStrategy();
        } else if (lessOrEqualThan(salario, new BigDecimal("800.00"))) {
            strategy = new BaixoMedioRangeSalarialReajusteStrategy();
        } else if (lessOrEqualThan(salario, new BigDecimal("1200.00"))) {
            strategy = new MedioRangeSalarialReajusteStrategy();
        } else if (lessOrEqualThan(salario, new BigDecimal("2000.00"))) {
            strategy = new MedioAltoRangeSalarialReajusteStrategy();
        } else {
            strategy = new AltoRangeSalarialReajusteStrategy();
        }
        return new ReajusteSalarialContext(strategy);
    }

    @Override
    public String getPercentualReajuste() {
        return strategy.getPercentualReajuste();
    }

    @Override
    public BigDecimal getNovoSalario(BigDecimal salarioAtual) {
        return strategy.getNovoSalario(salarioAtual);
    }

    @Override
    public BigDecimal getValorReajuste(BigDecimal salarioAtual) {
        return strategy.getValorReajuste(salarioAtual);
    }

    public void setStrategy(ReajusteSalarialStrategy strategy) {
        this.strategy = strategy;
    }
}
