package com.gabrielluciano.reajustesalarial.services.imposto.processors;

import com.gabrielluciano.reajustesalarial.exceptions.InvalidProcessorException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.gabrielluciano.reajustesalarial.util.bigdecimal.BigDecimalComparison.greaterThan;
import static com.gabrielluciano.reajustesalarial.util.bigdecimal.BigDecimalComparison.lessOrEqualThan;

public abstract class AbstractRangeSalarialImpostoProcessor extends AbstractImpostoRendaProcessor {

    private final BigDecimal taxa;
    private final BigDecimal limiteInferior;
    private final BigDecimal limiteSuperior;

    protected AbstractRangeSalarialImpostoProcessor() {
        taxa = getTaxa();
        limiteInferior = getLimiteInferior();
        limiteSuperior = getLimiteSuperior();
    }

    protected abstract BigDecimal getTaxa();

    protected abstract BigDecimal getLimiteInferior();

    protected abstract BigDecimal getLimiteSuperior();

    @Override
    public String calcularImposto(BigDecimal impostoAgregado, BigDecimal salario) {
        if (lessOrEqualThan(salario, limiteInferior))
            throw new InvalidProcessorException("Salário inferior ao limite inferior");

        BigDecimal imposto;
        if (greaterThan(salario, limiteSuperior)) {
            imposto = limiteSuperior.subtract(limiteInferior).multiply(taxa);
            if (nextProcessor != null)
                return nextProcessor.calcularImposto(impostoAgregado.add(imposto), salario);
        } else {
            imposto = impostoAgregado.add(salario.subtract(limiteInferior).multiply(taxa));
        }

        return String.format("Imposto: R$ %s", imposto.setScale(2, RoundingMode.FLOOR));
    }
}
