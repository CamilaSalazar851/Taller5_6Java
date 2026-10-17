package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

public class Main {

    static final int CURA_NORMAL = 30;
    static final int CURA_GRANDE = 60;
    static final double COSTO_POCION_GRANDE = 40.0;

    static Scanner sc = new Scanner(System.in);
    static int pocionesNormales = 2;
    static int pocionesGrandes = 0;

    public static void main(String[] args) {

        Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
        Enemigo enemigo = new Enemigo("Orco Bruto", 80, 12, 60.0, 60);
        StringBuilder historial = new StringBuilder();

        boolean huyo = false;
        int ronda = 0;

        System.out.printf("=== ARENA DE HÉROES ===%n");
        System.out.printf("%s se enfrenta a %s%n", heroe.getNombre(), enemigo.getNombre());

        do {
            ronda++;
            System.out.printf("%n--- Ronda %d ---%n", ronda);
            System.out.printf("1. Atacar%n2. Usar Poción%n3. Ver Estado%n4. Huir%n5. Tienda%n");
            System.out.printf("Elige una opción: ");
            int opcion = leerEntero();

            boolean turnoGastado = false;

            switch (opcion) {
                case 1:
                    int vidaEnemigoAntes = enemigo.getPuntosVida();
                    heroe.atacar(enemigo);
                    int danoCausado = vidaEnemigoAntes - enemigo.getPuntosVida();
                    historial.append(String.format("Ronda %d: %s ataca a %s y causa %d de daño%s.%n",
                            ronda, heroe.getNombre(), enemigo.getNombre(), danoCausado,
                            heroe.isUltimoGolpeCritico() ? " (¡GOLPE CRÍTICO!)" : ""));
                    turnoGastado = true;
                    break;
                case 2:
                    turnoGastado = usarPocion(heroe, historial, ronda);
                    break;
                case 3:
                    mostrarEstado(heroe, enemigo);
                    break;
                case 4:
                    huyo = true;
                    historial.append(String.format("Ronda %d: %s huye del combate.%n", ronda, heroe.getNombre()));
                    break;
                case 5:
                    abrirTienda(heroe, historial, ronda);
                    break;
                default:
                    System.out.printf("Opción no válida. Elige un número entre 1 y 5.%n");
            }

            // Turno del enemigo
            if (turnoGastado && enemigo.isEstaVivo()) {
                int vidaHeroeAntes = heroe.getPuntosVida();
                enemigo.atacar(heroe);
                historial.append(String.format("Ronda %d: %s ataca a %s y causa %d de daño.%n",
                        ronda, enemigo.getNombre(), heroe.getNombre(), vidaHeroeAntes - heroe.getPuntosVida()));
            }

            System.out.printf("%n[HISTORIAL DE BATALLA]%n%s", historial);

        } while (heroe.isEstaVivo() && enemigo.isEstaVivo() && !huyo);

        // Resultado final
        System.out.printf("%n=== FIN DEL COMBATE ===%n");
        if (huyo) {
            System.out.printf("%s escapó de la arena.%n", heroe.getNombre());
        } else if (!heroe.isEstaVivo()) {
            System.out.printf("%s ha sido derrotado por %s.%n", heroe.getNombre(), enemigo.getNombre());
        } else {
            System.out.printf("¡%s derrotó a %s!%n", heroe.getNombre(), enemigo.getNombre());
            heroe.setOro(heroe.getOro() + enemigo.getRecompensaOro());
            boolean subio = heroe.ganarExperiencia(enemigo.getExperienciaOtorgada());
            System.out.printf("Recompensa: %.2f de oro y %d de experiencia.%n",
                    enemigo.getRecompensaOro(), enemigo.getExperienciaOtorgada());
            if (subio) {
                System.out.printf("¡%s sube al nivel %d!%n", heroe.getNombre(), heroe.getNivel());
            }
        }
        mostrarEstado(heroe, enemigo);
    }

    static int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static void mostrarEstado(Personaje heroe, Enemigo enemigo) {
        System.out.printf("%n--- ESTADO ---%n");
        System.out.printf("%-10s Nivel: %d | Vida: %d/%d | Ataque: %d | Oro: %.2f | Vivo: %b%n",
                heroe.getNombre(), heroe.getNivel(), heroe.getPuntosVida(), heroe.getVidaMaxima(),
                heroe.getPuntosAtaque(), heroe.getOro(), heroe.isEstaVivo());
        System.out.printf("%-10s Vida: %d/%d | Ataque: %d | Vivo: %b%n",
                enemigo.getNombre(), enemigo.getPuntosVida(), enemigo.getVidaMaxima(),
                enemigo.getPuntosAtaque(), enemigo.isEstaVivo());
        System.out.printf("Pociones: %d normales, %d grandes%n", pocionesNormales, pocionesGrandes);
    }

    // Retorna true si se gastó el turno (se usó una poción).
    static boolean usarPocion(Personaje heroe, StringBuilder historial, int ronda) {
        if (pocionesNormales == 0 && pocionesGrandes == 0) {
            System.out.printf("No tienes pociones. Visita la tienda.%n");
            return false;
        }
        System.out.printf("1. Poción normal (+%d, quedan %d)%n2. Poción grande (+%d, quedan %d)%nElige: ",
                CURA_NORMAL, pocionesNormales, CURA_GRANDE, pocionesGrandes);
        int eleccion = leerEntero();

        int vidaAntes = heroe.getPuntosVida();
        if (eleccion == 1 && pocionesNormales > 0) {
            pocionesNormales--;
            heroe.usarPocion(CURA_NORMAL);
        } else if (eleccion == 2 && pocionesGrandes > 0) {
            pocionesGrandes--;
            heroe.usarPocion(CURA_GRANDE);
        } else {
            System.out.printf("Elección inválida o sin existencias.%n");
            return false;
        }
        historial.append(String.format("Ronda %d: %s usa una poción y recupera %d de vida.%n",
                ronda, heroe.getNombre(), heroe.getPuntosVida() - vidaAntes));
        return true;
    }

    static void abrirTienda(Personaje heroe, StringBuilder historial, int ronda) {
        System.out.printf("%n--- TIENDA ---%n");
        System.out.printf("Tu oro: %.2f%n", heroe.getOro());
        System.out.printf("1. Poción Grande de Salud (+%d vida) - %.2f de oro%n2. Volver%nElige: ",
                CURA_GRANDE, COSTO_POCION_GRANDE);
        int eleccion = leerEntero();

        if (eleccion == 1) {
            double nuevoSaldo = heroe.getOro() - COSTO_POCION_GRANDE;
            if (heroe.setOro(nuevoSaldo)) {
                pocionesGrandes++;
                System.out.printf("Compra exitosa. Oro restante: %.2f%n", heroe.getOro());
                historial.append(String.format("Ronda %d: %s compra una Poción Grande de Salud.%n",
                        ronda, heroe.getNombre()));
            } else {
                System.out.printf("Saldo insuficiente. La compra fue rechazada.%n");
            }
        } else if (eleccion != 2) {
            System.out.printf("Opción no válida.%n");
        }
    }
}
