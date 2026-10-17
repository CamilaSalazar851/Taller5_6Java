package com.taller.poo;

import java.util.Scanner;

public class SistemaGestionCuentas {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Arreglo de tamaño fijo, pre-poblado
        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        cuentas[0] = new CuentaBancaria("1001", "Carlos Pérez", 1250.75, 500.0);
        cuentas[1] = new CuentaBancaria("1002", "Ana Gómez");
        cuentas[2] = new CuentaBancaria("1003", "Luis Torres", 800.00, 200.0);

        int opcion;
        do {
            System.out.printf("%n===== SISTEMA DE GESTIÓN DE CUENTAS =====%n");
            System.out.printf("1. Depositar%n2. Retirar%n3. Ver reporte consolidado%n4. Salir%n");
            System.out.printf("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    operar(cuentas, true);
                    break;
                case 2:
                    operar(cuentas, false);
                    break;
                case 3:
                    mostrarReporte(cuentas);
                    break;
                case 4:
                    System.out.printf("Cerrando el sistema. ¡Hasta pronto!%n");
                    break;
                default:
                    System.out.printf("Opción %d no válida. Elige entre 1 y 4.%n", opcion);
            }
        } while (opcion != 4);
    }

    // Lee una línea y la convierte a entero; si no es número devuelve -1.
    static int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Recorre el arreglo buscando la cuenta por su número.
    static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numero) {
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(numero)) {
                return cuentas[i];
            }
        }
        return null;
    }

    // Maneja tanto depósito (true) como retiro (false).
    static void operar(CuentaBancaria[] cuentas, boolean esDeposito) {
        System.out.printf("Número de cuenta: ");
        String numero = sc.nextLine().trim();

        CuentaBancaria cuenta = buscarCuenta(cuentas, numero);
        if (cuenta == null) {
            System.out.printf("Error: la cuenta %s no existe.%n", numero);
            return;
        }

        System.out.printf("Monto: ");
        double monto;
        try {
            monto = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.printf("Error: el monto debe ser un número.%n");
            return;
        }

        if (monto <= 0) {
            System.out.printf("Error: el monto debe ser mayor a cero.%n");
            return;
        }

        boolean exito = esDeposito ? cuenta.depositar(monto) : cuenta.retirar(monto);
        if (exito) {
            System.out.printf("%s exitoso. Nuevo saldo: %s%n",
                    esDeposito ? "Depósito" : "Retiro", ConversorMoneda.formatearMoneda(cuenta.getSaldo()));
        } else {
            System.out.printf("No se pudo realizar la operación (fondos insuficientes).%n");
        }
    }

    static void mostrarReporte(CuentaBancaria[] cuentas) {
        StringBuilder detalle = new StringBuilder();
        double total = 0.0;

        for (int i = 0; i < cuentas.length; i++) {
            CuentaBancaria c = cuentas[i];
            detalle.append(String.format("Cuenta: %s | Titular: %-14s | Saldo: %s%n",
                    c.getNumeroCuenta(), c.getTitular(), ConversorMoneda.formatearMoneda(c.getSaldo())));
            total += c.getSaldo();
        }

        System.out.printf("%n=== REPORTE CONSOLIDADO ===%n");
        System.out.printf("%s", detalle);
        System.out.printf("Saldo total acumulado: %s%n", ConversorMoneda.formatearMoneda(total));
    }
}
