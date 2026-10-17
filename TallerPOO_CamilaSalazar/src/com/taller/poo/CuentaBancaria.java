package com.taller.poo;

public class CuentaBancaria {
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

    /*
     * PREGUNTAS DE ANÁLISIS:
     * 1. ¿Qué diferencia ocurre a nivel de memoria y asignación por defecto al
     * declarar 'saldo' como double primitivo frente a 'limiteCredito' como Wrapper Double?
     *
     * RESPUESTA: 'saldo' es un primitivo: el valor (8 bytes) vive directamente dentro del
     * objeto y, si no se asigna, Java lo inicia en 0.0. 'limiteCredito' es un Wrapper: el
     * objeto solo guarda una referencia que apunta a otro objeto Double en el Heap (más
     * memoria) y, si no se asigna, su valor por defecto es null, o sea "no apunta a nada".
     *
     * 2. ¿Por qué una operación aritmética sobre un atributo no inicializado de
     * tipo Double provoca una NullPointerException, mientras que sobre un double
     * primitivo opera con 0.0?
     *
     * RESPUESTA: Para operar, Java debe convertir el Double a double (unboxing), es decir,
     * llamar internamente a limiteCredito.doubleValue(). Si la referencia es null no hay
     * objeto sobre el cual llamar ese método, y de ahí la NullPointerException. El primitivo
     * no es una referencia: siempre tiene un valor (0.0 por defecto), así que opera normal.
     */

    // Constructor por defecto
    public CuentaBancaria() {
        this.numeroCuenta = "000000";
        this.titular = "Sin Nombre";
        this.saldo = 0.0;
        this.limiteCredito = 0.0;
    }

    // Constructor de 2 parámetros
    public CuentaBancaria(String numeroCuenta, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.limiteCredito = 100.0;
    }

    // Constructor completo
    public CuentaBancaria(String numeroCuenta, String titular, double saldo, Double limiteCredito) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
    }

    public boolean depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        } else {
            return false;
        }
    }

    public boolean retirar(double monto) {
        // Si el límite es null, se toma como 0 para evitar NullPointerException.
        double credito = (limiteCredito != null) ? limiteCredito : 0.0;
        if (monto > 0) {
            if (saldo + credito >= monto) {
                this.saldo -= monto;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }
}
