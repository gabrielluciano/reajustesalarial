package com.gabrielluciano.reajustesalarial.strategies;

import java.math.BigDecimal;

public class ReajusteSalarialProcessor implements ReajusteSalarialStrategy {

    private ReajusteSalarialStrategy strategy;

    private ReajusteSalarialProcessor(ReajusteSalarialStrategy strategy) {
        this.strategy = strategy;
    }

    public static ReajusteSalarialProcessor fromSalario(BigDecimal salario) {
        ReajusteSalarialStrategy strategy;
        if (lessOrEqualThan(salario, "400.00")) {
            strategy = new BaixoRangeSalarialReajusteStrategy();
        } else if (lessOrEqualThan(salario, "800.00")) {
            strategy = new BaixoMedioRangeSalarialReajusteStrategy();
        } else if (lessOrEqualThan(salario, "1200.00")) {
            strategy = new MedioRangeSalarialReajusteStrategy();
        } else if (lessOrEqualThan(salario, "2000.00")) {
            strategy = new MedioAltoRangeSalarialReajusteStrategy();
        } else {
            strategy = new AltoRangeSalarialReajusteStrategy();
        }
        return new ReajusteSalarialProcessor(strategy);
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

    private static boolean lessOrEqualThan(BigDecimal salario, String value) {
        return salario.compareTo(new BigDecimal(value)) <= 0;
    }

    public void setStrategy(ReajusteSalarialStrategy strategy) {
        this.strategy = strategy;
    }
}
