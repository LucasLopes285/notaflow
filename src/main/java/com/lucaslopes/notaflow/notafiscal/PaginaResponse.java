package com.lucaslopes.notaflow.notafiscal;

import org.springframework.data.domain.Page;
import java.util.List;

public record PaginaResponse<T>(
        List<T> conteudo,
        int paginaAtual,
        int totalPaginas,
        long totalDeItens
) {
    public static <T> PaginaResponse<T> fromPage(Page<T> page) {
        return new PaginaResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}