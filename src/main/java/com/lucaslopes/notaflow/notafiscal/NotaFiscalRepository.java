package com.lucaslopes.notaflow.notafiscal;

import  org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long>{
    Optional<NotaFiscal> findByNumeroAndCnpjEmissor(String numero, String cnpjEmissor);
}
