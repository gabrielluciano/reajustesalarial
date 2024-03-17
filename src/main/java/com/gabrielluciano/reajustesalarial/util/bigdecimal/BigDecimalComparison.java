package com.gabrielluciano.reajustesalarial.util.bigdecimal;

import java.math.BigDecimal;

public class BigDecimalComparison {

    public static boolean lessOrEqualThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) <= 0;
    }

    public static boolean greaterThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > 0;
    }
}
