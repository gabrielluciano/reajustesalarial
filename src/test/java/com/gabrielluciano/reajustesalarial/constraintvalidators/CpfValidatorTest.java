package com.gabrielluciano.reajustesalarial.constraintvalidators;

import com.gabrielluciano.reajustesalarial.util.constraintvalidators.CpfValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CpfValidatorTest {

    @Test
    void givenValidCpf_whenIsValid_thenReturnTrue() {
        assertValidCpf("640.857.140-90");
        assertValidCpf("818.455.820-12");
        assertValidCpf("250.803.350-25");
        assertValidCpf("526.996.260-82");
        assertValidCpf("211.162.980-40");
    }

    @Test
    void givenCpfWithAllDigitsTheSame_whenIsValid_thenReturnFalse() {
        assertInvalidCpf("000.000.000-00");
        assertInvalidCpf("111.111.111-11");
        assertInvalidCpf("222.222.222-22");
        assertInvalidCpf("333.333.333-33");
        assertInvalidCpf("444.444.444-44");
        assertInvalidCpf("555.555.555-55");
        assertInvalidCpf("666.666.666-66");
        assertInvalidCpf("777.777.777-77");
        assertInvalidCpf("888.888.888-88");
        assertInvalidCpf("999.999.999-99");
    }

    @Test
    void givenCpfWithWrongFormat_whenIsValid_thenReturnFalse() {
        // Numbers are valid but the format is not
        assertInvalidCpf("64085714090");
        assertInvalidCpf("81845582012");
    }

    @Test
    void givenCpfWithWrongVerifiers_whenIsValid_thenReturnFalse() {
        // Numbers are valid but the format is not
        assertInvalidCpf("640.857.140-91");
        assertInvalidCpf("818.455.820-00");
        assertInvalidCpf("250.803.350-52");
        assertInvalidCpf("526.996.260-10");
        assertInvalidCpf("211.162.980-01");
    }

    private void assertValidCpf(String cpf) {
        CpfValidator validator = new CpfValidator();
        boolean result = validator.isValid(cpf, null);
        assertTrue(result);
    }

    private void assertInvalidCpf(String cpf) {
        CpfValidator validator = new CpfValidator();
        boolean result = validator.isValid(cpf, null);
        assertFalse(result);
    }
}
