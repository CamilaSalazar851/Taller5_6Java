package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;

// Prueba manual de la guía 6.2: valores límite -10, 0 y 100000 en los setters.
public class PruebaLimites {

    public static void main(String[] args) {
        int[] valores = {-10, 0, 50, 100000};

        for (int valor : valores) {
            Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
            heroe.setPuntosVida(valor);
            System.out.printf("setPuntosVida(%d) -> vida: %d | vivo: %b%n",
                    valor, heroe.getPuntosVida(), heroe.isEstaVivo());
        }

        Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
        boolean aceptado = heroe.setOro(-10.0);
        System.out.printf("setOro(-10.00) -> aceptado: %b | oro: %.2f%n", aceptado, heroe.getOro());
        aceptado = heroe.setOro(0.0);
        System.out.printf("setOro(0.00) -> aceptado: %b | oro: %.2f%n", aceptado, heroe.getOro());
    }
}
