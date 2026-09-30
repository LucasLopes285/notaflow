package com.lucaslopes.notaflow.notafiscal;

import com.lucaslopes.notaflow.notafiscal.Extrator;
import com.lucaslopes.notaflow.notafiscal.ResultadoExtracao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ExtratorMock implements Extrator {

    @Override
    public ResultadoExtracao extrair(String caminhoImagem) {
        if (caminhoImagem != null && caminhoImagem.contains("falha-transitoria")) {
            throw new ErroTransitorioException("Falha simulada de rede/recurso", null);
        }
        if (caminhoImagem != null && caminhoImagem.contains("falha-permanente")) {
            throw new ErroPermanenteException("Imagem ilegível simulada", null);
        }

        String numeroUnico = "MOCK-" + System.currentTimeMillis();
        return new ResultadoExtracao(
                numeroUnico, "11222333000181", LocalDate.now(), new BigDecimal("42.00")
        );
    }
}