package co.edu.puj.rpg.modelo.enemigo;

import co.edu.puj.rpg.modelo.Personaje;

public class Enemigo {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private boolean estaVivo;
    private double recompensaOro;
    private int experienciaOtorgada;

    public Enemigo(String nombre, int vidaMaxima, int puntosAtaque, double recompensaOro, int experienciaOtorgada) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.puntosAtaque = puntosAtaque;
        this.recompensaOro = recompensaOro;
        this.experienciaOtorgada = experienciaOtorgada;
        this.puntosVida = vidaMaxima;
        this.estaVivo = true;
    }

    public void atacar(Personaje heroe) {
        if (estaVivo && heroe.isEstaVivo()) {
            heroe.recibirDano(puntosAtaque);
        }
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano > 0) {
            setPuntosVida(puntosVida - cantidadDano);
        }
    }

    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > vidaMaxima) {
            this.puntosVida = vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getPuntosAtaque() {
        return puntosAtaque;
    }

    public boolean isEstaVivo() {
        return estaVivo;
    }

    public double getRecompensaOro() {
        return recompensaOro;
    }

    public int getExperienciaOtorgada() {
        return experienciaOtorgada;
    }
}
