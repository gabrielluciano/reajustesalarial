package com.gabrielluciano.reajustesalarial.services.imposto.processors;

import com.gabrielluciano.reajustesalarial.exceptions.InvalidProcessorException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.gabrielluciano.reajustesalarial.util.bigdecimal.BigDecimalComparison.lessOrEqualThan;

public class AltoRangeSalarialImpostoProcessor extends AbstractImpostoRendaProcessor {

    private static final BigDecimal taxa = new BigDecimal("0.28");
    private static final BigDecimal limiteInferior = new BigDecimal("4500.00");

    @Override
    public String calcularImposto(BigDecimal impostoAgregado, BigDecimal salario) {
        if (lessOrEqualThan(salario, limiteInferior))
            throw new InvalidProcessorException("Salário inferior ao limite inferior");

        BigDecimal imposto = impostoAgregado.add(salario.subtract(limiteInferior).multiply(taxa));
        return String.format("Imposto: R$ %s", imposto.setScale(2, RoundingMode.FLOOR));
    }
}
