package com.lucaslopes.notaflow.notafiscal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CnpjValidator implements ConstraintValidator<CnpjValido, String> {

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        return ValidadorCnpj.isValido(cnpj);
    }
}