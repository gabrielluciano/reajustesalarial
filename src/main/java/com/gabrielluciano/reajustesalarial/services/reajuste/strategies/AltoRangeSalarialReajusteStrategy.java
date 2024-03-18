package com.gabrielluciano.reajustesalarial.services.reajuste.strategies;

import java.math.BigDecimal;

public class AltoRangeSalarialReajusteStrategy extends BaseRangeSalarialReajusteStrategy {

    @Override
    protected BigDecimal getTaxa() {
        return new BigDecimal("0.04");
    }
}
