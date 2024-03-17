package com.gabrielluciano.reajustesalarial.strategies.impostorenda.handlers;

import java.math.BigDecimal;

public class MedioRangeSalarialImpostoProcessor extends AbstractRangeSalarialImpostoProcessor {

    @Override
    protected BigDecimal getTaxa() {
        return new BigDecimal("0.18");
    }

    @Override
    protected BigDecimal getRangeInferior() {
        return new BigDecimal("3000.00");
    }

    @Override
    protected BigDecimal getRangeSuperior() {
        return new BigDecimal("4500.00");
    }
}
