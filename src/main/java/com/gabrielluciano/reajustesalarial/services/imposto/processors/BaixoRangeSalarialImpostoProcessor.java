package com.gabrielluciano.reajustesalarial.services.imposto.processors;

import java.math.BigDecimal;

import static com.gabrielluciano.reajustesalarial.util.bigdecimal.BigDecimalComparison.lessOrEqualThan;

public class BaixoRangeSalarialImpostoProcessor extends AbstractImpostoRendaProcessor {

    private static final BigDecimal limiteSuperior = new BigDecimal("2000.00");

    @Override
    public String calcularImposto(BigDecimal impostoAgregado, BigDecimal salario) {
        if (lessOrEqualThan(salario, limiteSuperior) || nextProcessor == null)
            return "Isento";
        return nextProcessor.calcularImposto(impostoAgregado, salario);
    }
}
