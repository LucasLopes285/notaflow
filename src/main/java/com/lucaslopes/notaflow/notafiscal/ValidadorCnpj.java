package com.lucaslopes.notaflow.notafiscal;

public final class ValidadorCnpj {

    private static final int[] PESOS_PRIMEIRO_DIGITO = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS_SEGUNDO_DIGITO  = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private ValidadorCnpj() {

    }

    public static boolean isValido(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")){
            return false;
        }
        if (todosDigitosIguais(cnpj)){
            return false;
        }

        int[] digitos = paraArrayDeDigitos(cnpj);

        int primeiroDigitoCalculado = calcularDigitoVerificador(digitos, PESOS_PRIMEIRO_DIGITO, 12);
        int segundoDigitoCalculado = calcularDigitoVerificador(digitos, PESOS_SEGUNDO_DIGITO, 13);

        return digitos[12] == primeiroDigitoCalculado && digitos[13] == segundoDigitoCalculado;

    }

    private static int calcularDigitoVerificador(int[] digitos, int[] pesos, int quantidade) {
       int soma = 0;
       for (int i = 0; i < quantidade; i++){
           soma += digitos[i] * pesos[i];
       }
       int resto = soma % 11;
       return (resto < 2) ? 0 : (11 - resto);
    }

    private static boolean todosDigitosIguais(String cnpj) {
        return cnpj.chars().distinct().count() == 1;
    }

    private static int[] paraArrayDeDigitos(String cnpj) {
        int[] digitos = new int[cnpj.length()];
        for (int i = 0; i < cnpj.length(); i++) {
            digitos[i] = Character.getNumericValue(cnpj.charAt(i));
        }
        return digitos;
    }
}
