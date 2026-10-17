package com.taller.poo;

/*
 * Primitivos vs Wrappers en esta clase:
 * - Un primitivo (double, int, boolean) guarda el valor directamente, ocupa poca memoria
 *   (8, 4 y 1 byte aproximadamente) y nunca puede ser null: si no se asigna, arranca en
 *   0.0, 0 o false.
 * - Un Wrapper (Double, Integer, Boolean) es un objeto en el Heap: consume más memoria
 *   (cabecera del objeto + referencia) y además puede valer null, lo que sirve cuando
 *   "no tener dato" es un estado válido, pero obliga a revisar null para evitar
 *   NullPointerException.
 * En Vehiculo se eligieron primitivos porque siempre hay un valor real (el constructor
 * por defecto asigna 0.0, 120 y false) y no se necesita representar "dato desconocido".
 */
public class Vehiculo {

    private String marca;
    private String modelo;
    // double primitivo: el kilometraje siempre existe (empieza en 0.0), nunca es "desconocido".
    private double kilometraje;
    // int primitivo: la velocidad máxima siempre tiene un valor concreto, ocupa menos memoria.
    private int velocidadMaxima;
    // boolean primitivo: el motor solo está encendido o apagado, no hay un tercer estado null.
    private boolean encendido;

    // Constructor por defecto
    public Vehiculo() {
        this.marca = "Genérico";
        this.modelo = "Sin Modelo";
        this.kilometraje = 0.0;
        this.velocidadMaxima = 120;
        this.encendido = false;
    }

    // Constructor parametrizado
    public Vehiculo(String marca, String modelo, double kilometraje, int velocidadMaxima, boolean encendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public boolean isEncendido() {
        return encendido;
    }
}
