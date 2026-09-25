package com.lucaslopes.notaflow.notafiscal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResultadoExtracao(
        String numero,
        String cnpjEmissor,
        LocalDate dataEmissao,
        BigDecimal valorTotal
) {
}