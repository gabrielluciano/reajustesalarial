package com.gabrielluciano.reajustesalarial.services.imposto.processors;

import java.math.BigDecimal;

public abstract class AbstractImpostoRendaProcessor {

    public AbstractImpostoRendaProcessor nextProcessor;

    public abstract String calcularImposto(BigDecimal impostoAgregado, BigDecimal salario);

    public void setNextProcessor(AbstractImpostoRendaProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }
}
