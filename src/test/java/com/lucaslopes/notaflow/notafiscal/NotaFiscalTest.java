package com.lucaslopes.notaflow.notafiscal;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class NotaFiscalTest {

    @Test
    void deveComecarComStatusPendente() {
        NotaFiscal nota = new NotaFiscal(
                "12345", "12345678000199", LocalDate.now(), new BigDecimal("100.00")
        );

        assertThat(nota.getStatus()).isEqualTo(StatusNota.PENDENTE);
    }

    @Test
    void deveMudarParaConcluidaQuandoMarcada() {
        NotaFiscal nota = new NotaFiscal(
                "12345", "12345678000199", LocalDate.now(), new BigDecimal("100.00")
        );

        nota.marcarComoConcluida();

        assertThat(nota.getStatus()).isEqualTo(StatusNota.CONCLUIDA);
    }

    @Test
    void deveMudarParaFalhaQuandoMarcada() {
        NotaFiscal nota = new NotaFiscal(
                "12345", "12345678000199", LocalDate.now(), new BigDecimal("100.00")
        );

        nota.marcarComoFalha();

        assertThat(nota.getStatus()).isEqualTo(StatusNota.FALHA);
    }
}