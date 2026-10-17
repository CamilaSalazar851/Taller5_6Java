package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;

public class Personaje {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;

    // Apoyo para los retos de experiencia y críticos
    private int experiencia;
    private boolean ultimoGolpeCritico;

    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.puntosAtaque = puntosAtaque;
        this.oro = oro;
        this.nivel = 1;
        this.experiencia = 0;
        this.ultimoGolpeCritico = false;
        this.estaVivo = true;
        this.puntosVida = vidaMaxima;
    }

    // Constructor sobrecargado con valores por defecto
    public Personaje(String nombre) {
        this(nombre, 100, 10, 0.0);
    }

    // ---------- API de comportamiento ----------

    public void atacar(Enemigo enemigo) {
        if (!estaVivo || !enemigo.isEstaVivo()) {
            return;
        }
        // 15% de probabilidad de golpe crítico (daño doble)
        ultimoGolpeCritico = Math.random() < 0.15;
        int dano = ultimoGolpeCritico ? puntosAtaque * 2 : puntosAtaque;
        enemigo.recibirDano(dano);
    }

    public void usarPocion(int cantidadRestaurada) {
        if (estaVivo && cantidadRestaurada > 0) {
            setPuntosVida(puntosVida + cantidadRestaurada);
        }
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano > 0) {
            setPuntosVida(puntosVida - cantidadDano);
        }
    }

    // Suma experiencia y sube de nivel si supera el umbral (nivel * 50). Retorna true si subió.
    public boolean ganarExperiencia(int puntos) {
        boolean subio = false;
        if (puntos > 0) {
            experiencia += puntos;
            while (experiencia >= nivel * 50) {
                experiencia -= nivel * 50;
                setNivel(nivel + 1);
                subio = true;
            }
        }
        return subio;
    }

    // ---------- Setters con validación ----------

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

    // Retorna false (sin cambiar nada) si el nuevo saldo sería negativo.
    public boolean setOro(double oro) {
        if (oro < 0.0) {
            return false;
        }
        this.oro = oro;
        return true;
    }

    // Protegido: el nivel solo cambia por la lógica interna (experiencia).
    protected void setNivel(int nivel) {
        if (nivel >= 1) {
            this.nivel = nivel;
        }
    }

    // ---------- Getters ----------

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

    public int getNivel() {
        return nivel;
    }

    public double getOro() {
        return oro;
    }

    public boolean isEstaVivo() {
        return estaVivo;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public boolean isUltimoGolpeCritico() {
        return ultimoGolpeCritico;
    }
}
