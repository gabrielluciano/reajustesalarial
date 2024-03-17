package com.gabrielluciano.reajustesalarial.strategies.impostorenda.handlers;

import java.math.BigDecimal;

public abstract class AbstractImpostoRendaProcessor {

    public AbstractImpostoRendaProcessor nextProcessor;

    public abstract String calcularImposto(BigDecimal impostoAgregado, BigDecimal salario);

    public void setNextProcessor(AbstractImpostoRendaProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }
}
