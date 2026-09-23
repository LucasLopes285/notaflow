package com.lucaslopes.notaflow.notafiscal;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record NotaFiscalRequest(
        @NotBlank(message = "número é obrigatório")
        String numero,

        @NotBlank(message = "CNPJ é obrigatório")
        @CnpjValido(message = "CNPJ inválido")
        String cnpjEmissor,

        @NotNull(message = "data de emissão é obrigatória")
        @PastOrPresent(message = "data de emissão não pode ser no futuro")
        LocalDate dataEmissao,

        @NotNull(message = "valor total é obrigatório")
        @Positive(message = "valor total deve ser positivo")
        BigDecimal valorTotal
) {}
