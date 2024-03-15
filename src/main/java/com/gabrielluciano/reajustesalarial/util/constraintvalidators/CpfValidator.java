package com.gabrielluciano.reajustesalarial.util.constraintvalidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CpfValidator implements ConstraintValidator<CpfConstraint, String> {

    public static final Pattern validFormatPattern = Pattern.compile("([0-9]{3}[.][0-9]{3}[.][0-9]{3}-[0-9]{2})");
    public static final Pattern sameDigitsPattern = Pattern.compile("^(\\d)\\1{2}\\.\\1{3}\\.\\1{3}-\\1{2}$");

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        if (!validFormatPattern.matcher(cpf).matches())
            return false;

        // Special case where all digits are the same is considered invalid
        if (sameDigitsPattern.matcher(cpf).matches())
            return false;

        int[] digits = parseDigits(cpf);
        int v1 = calculateFirstVerifier(digits);
        int v2 = calculateSecondVerifier(digits);

        return v1 == digits[digits.length - 2] && v2 == digits[digits.length - 1];
    }

    private int[] parseDigits(String cpf) {
        String[] stringDigits = cpf.replaceAll("[-\\.]", "").split("");
        int[] digits = new int[stringDigits.length];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = Integer.parseInt(stringDigits[i]);
        }
        return digits;
    }

    private int calculateFirstVerifier(int[] digits) {
        final int numberOfDigitsBefore = 9;
        int v1 = 0;
        for (int i = 0; i < numberOfDigitsBefore; i++) {
            v1 += digits[i] * (10 - i);
        }
        int mod = v1 % 11;
        return mod < 2 ? 0 : 11 - mod;
    }

    private int calculateSecondVerifier(int[] digits) {
        final int numberOfDigitsBefore = 10;
        int v2 = 0;
        for (int i = 0; i < numberOfDigitsBefore; i++) {
            v2 += digits[i] * (11 - i);
        }
        int mod = v2 % 11;
        return mod < 2 ? 0 : 11 - mod;
    }
}
