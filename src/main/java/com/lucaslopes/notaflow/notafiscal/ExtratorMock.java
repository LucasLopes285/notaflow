package com.lucaslopes.notaflow.notafiscal;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ExtratorMock implements Extrator {

    @Override
    public ResultadoExtracao extrair(String caminhoImagem) {
        return new ResultadoExtracao(
                "MOCK-0001",
                "11222333000181",
                LocalDate.now(),
                new BigDecimal("42.00")
        );
    }
}