package com.gabrielluciano.reajustesalarial.strategies.reajustesalarial;

import java.math.BigDecimal;

public class BaixoRangeSalarialReajusteStrategy extends BaseRangeSalarialReajusteStrategy {

    @Override
    protected BigDecimal getTaxa() {
        return new BigDecimal("0.15");
    }
}
