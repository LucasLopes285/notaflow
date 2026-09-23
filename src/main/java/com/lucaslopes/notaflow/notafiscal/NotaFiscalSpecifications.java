package com.lucaslopes.notaflow.notafiscal;

import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.LocalDate;

public class NotaFiscalSpecifications {

    private NotaFiscalSpecifications() {
    }

    public static Specification<NotaFiscal> comCnpjEmissor(String cnpj) {
        return (root, query, cb) -> cb.equal(root.get("cnpjEmissor"), cnpj);
    }

    public static Specification<NotaFiscal> comDataEmissaoApartirDe(LocalDate de) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dataEmissao"), de);
    }

    public static Specification<NotaFiscal> comDataEmissaoAte(LocalDate ate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("dataEmissao"), ate);
    }

    public static Specification<NotaFiscal> comValorMinimo(BigDecimal valorMinimo) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("valorTotal"), valorMinimo);
    }

    public static Specification<NotaFiscal> comValorMaximo(BigDecimal valorMaximo) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("valorTotal"), valorMaximo);
    }
}