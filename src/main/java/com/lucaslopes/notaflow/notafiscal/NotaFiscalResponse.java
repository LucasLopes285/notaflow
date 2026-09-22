package com.lucaslopes.notaflow.notafiscal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NotaFiscalResponse(
        Long id,
        String numero,
        String cnpjEmissor,
        LocalDate dataEmissao,
        BigDecimal valorTotal,
        StatusNota status
) {
    public static NotaFiscalResponse fromEntity(NotaFiscal nota) {
        return new NotaFiscalResponse(
                nota.getId(), nota.getNumero(), nota.getCnpjEmissor(),
                nota.getDataEmissao(), nota.getValorTotal(), nota.getStatus()
        );
    }
}
