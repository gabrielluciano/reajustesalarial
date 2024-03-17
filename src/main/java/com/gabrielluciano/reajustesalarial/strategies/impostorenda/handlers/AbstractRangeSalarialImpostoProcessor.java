package com.gabrielluciano.reajustesalarial.strategies.impostorenda.handlers;

import com.gabrielluciano.reajustesalarial.strategies.impostorenda.handlers.AbstractImpostoRendaProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.gabrielluciano.reajustesalarial.util.bigdecimal.BigDecimalComparison.greaterThan;
import static com.gabrielluciano.reajustesalarial.util.bigdecimal.BigDecimalComparison.lessOrEqualThan;

public abstract class AbstractRangeSalarialImpostoProcessor extends AbstractImpostoRendaProcessor {

    private final BigDecimal taxa;
    private final BigDecimal rangeInferior;
    private final BigDecimal rangeSuperior;


    public AbstractRangeSalarialImpostoProcessor() {
        taxa = getTaxa();
        rangeInferior = getRangeInferior();
        rangeSuperior = getRangeSuperior();
    }

    protected abstract BigDecimal getTaxa();

    protected abstract BigDecimal getRangeInferior();

    protected abstract BigDecimal getRangeSuperior();

    @Override
    public String calcularImposto(BigDecimal impostoAgregado, BigDecimal salario) {
        if (lessOrEqualThan(salario, rangeInferior))
            throw new RuntimeException("Salário inválido para este processor");

        BigDecimal imposto;
        if (greaterThan(salario, rangeSuperior)) {
            imposto = rangeSuperior.subtract(rangeInferior).multiply(taxa);
            if (nextProcessor != null)
                return nextProcessor.calcularImposto(impostoAgregado.add(imposto), salario);
        } else {
            imposto = impostoAgregado.add(salario.subtract(rangeInferior).multiply(taxa));
        }

        return String.format("Imposto: R$ %s", imposto.setScale(2, RoundingMode.FLOOR));
    }
}
