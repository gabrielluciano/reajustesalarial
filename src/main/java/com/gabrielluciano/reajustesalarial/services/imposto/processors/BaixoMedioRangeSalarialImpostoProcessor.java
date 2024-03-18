package com.gabrielluciano.reajustesalarial.services.imposto.processors;

import java.math.BigDecimal;

public class BaixoMedioRangeSalarialImpostoProcessor extends AbstractRangeSalarialImpostoProcessor {

    @Override
    protected BigDecimal getTaxa() {
        return new BigDecimal("0.08");
    }

    @Override
    protected BigDecimal getRangeInferior() {
        return new BigDecimal("2000.00");
    }

    @Override
    protected BigDecimal getRangeSuperior() {
        return new BigDecimal("3000.00");
    }
}
