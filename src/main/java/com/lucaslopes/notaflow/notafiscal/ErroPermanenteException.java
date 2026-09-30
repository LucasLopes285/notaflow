package com.lucaslopes.notaflow.notafiscal;

public class ErroPermanenteException extends RuntimeException {
    public ErroPermanenteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
