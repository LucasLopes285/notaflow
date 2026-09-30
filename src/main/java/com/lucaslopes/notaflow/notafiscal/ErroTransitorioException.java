package com.lucaslopes.notaflow.notafiscal;

public class ErroTransitorioException extends RuntimeException{
    public ErroTransitorioException(String mensagem,Throwable causa){
        super(mensagem, causa);
    }
}
