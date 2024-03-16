package com.gabrielluciano.reajustesalarial.util;

import com.gabrielluciano.reajustesalarial.dto.ReajusteResponse;

import java.math.BigDecimal;

public class ReajusteResponseCreator {

    public static ReajusteResponse createValidReajusteResponse() {
        ReajusteResponse reajusteResponse = new ReajusteResponse();
        reajusteResponse.setPercentualReajuste("10%");
        reajusteResponse.setValorReajuste(new BigDecimal("100.00"));
        reajusteResponse.setNovoSalario(new BigDecimal("1100.00"));
        reajusteResponse.setCpf("193.386.850-39");
        return reajusteResponse;
    }
}
