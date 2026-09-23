package com.lucaslopes.notaflow.notafiscal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;


public class ValidadorCnpjTest {

    @Test
    void deveAceitarCnpjValido() {
        assertThat(ValidadorCnpj.isValido("11222333000181")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "11222333000180",  // último dígito errado
            "11222333000171",  // penúltimo dígito errado
            "1234567890123",   // 13 dígitos (curto demais)
            "123456789012345", // 15 dígitos (longo demais)
            "abcdefghijklmn",  // não numérico
            "11111111111111"   // todos os dígitos iguais (matematicamente
    })
    void deveRejeitarCnpjInvalido(String cnpjInvalido) {
        assertThat(ValidadorCnpj.isValido(cnpjInvalido)).isFalse();
    }

    @Test
    void deveRejeitarNulo(){
        assertThat(ValidadorCnpj.isValido(null)).isFalse();
    }
}
