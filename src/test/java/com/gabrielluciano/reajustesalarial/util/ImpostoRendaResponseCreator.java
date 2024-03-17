package com.gabrielluciano.reajustesalarial.util;

import com.gabrielluciano.reajustesalarial.dto.ImpostoRendaResponse;

public class ImpostoRendaResponseCreator {

    public static ImpostoRendaResponse createValidImpostoRendaResponse() {
        ImpostoRendaResponse impostoRendaResponse = new ImpostoRendaResponse();
        impostoRendaResponse.setCpf("193.386.850-39");
        impostoRendaResponse.setImposto("Imposto: R$ 45.33");
        return impostoRendaResponse;
    }
}
