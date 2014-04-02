package br.com.unirio.cr.utils;

public class CalculationUtil {

    public static Double calculateCr(Long[] creditos, Double[] notas) {
        Double numerador = 0.0;
        Long denominador = 0L;

        for (int i = 0; i < creditos.length; i++) {
            numerador += (notas[i] * creditos[i]);
            denominador += creditos[i];
        }

        return (denominador == 0 ? 0 : numerador / denominador);
    }

    public static Long calculateCreditos(Long[] creditos) {
        Long credito = 0L;
        for (int i = 0; i < creditos.length; i++) {
            credito += creditos[i];
        }
        return credito;
    }
}
