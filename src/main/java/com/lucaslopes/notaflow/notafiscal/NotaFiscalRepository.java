package com.lucaslopes.notaflow.notafiscal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface NotaFiscalRepository extends
        JpaRepository<NotaFiscal, Long>,
        JpaSpecificationExecutor<NotaFiscal> {

    Optional<NotaFiscal> findByNumeroAndCnpjEmissor(String numero, String cnpjEmissor);
}