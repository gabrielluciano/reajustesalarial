package com.gabrielluciano.reajustesalarial.strategies.impostorenda;

import com.gabrielluciano.reajustesalarial.strategies.impostorenda.handlers.*;

import java.math.BigDecimal;

public class ImpostoRendaCalculator {

    private final AbstractImpostoRendaProcessor processor;

    public ImpostoRendaCalculator() {
        BaixoRangeSalarialImpostoProcessor processor1 = new BaixoRangeSalarialImpostoProcessor();
        BaixoMedioRangeSalarialImpostoProcessor processor2 = new BaixoMedioRangeSalarialImpostoProcessor();
        MedioRangeSalarialImpostoProcessor processor3 = new MedioRangeSalarialImpostoProcessor();
        AltoRangeSalarialImpostoProcessor processor4 = new AltoRangeSalarialImpostoProcessor();

        processor1.setNextProcessor(processor2);
        processor2.setNextProcessor(processor3);
        processor3.setNextProcessor(processor4);

        this.processor = processor1;
    }

    public String calcularImposto(BigDecimal salario) {
        return processor.calcularImposto(new BigDecimal("0.00"), salario);
    }
}
