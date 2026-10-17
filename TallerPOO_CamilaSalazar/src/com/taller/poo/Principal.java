package com.taller.poo;

public class Principal {

    public static void main(String[] args) {

        // 3 vehículos: 1 con constructor por defecto y 2 parametrizados
        Vehiculo[] vehiculos = {
                new Vehiculo("Toyota", "Corolla", 15000.50, 180, true),
                new Vehiculo(),
                new Vehiculo("Mazda", "CX-5", 32000.00, 200, false)
        };

        // 3 estudiantes
        Estudiante[] estudiantes = {
                new Estudiante("E001", "Laura Martínez", 4.35, true),
                new Estudiante("E002", "Andrés Rojas", 3.80, true),
                new Estudiante("E003", "Sofía Herrera", 2.95, false)
        };

        // 3 cuentas, una con cada constructor
        CuentaBancaria cuenta1 = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.0);
        CuentaBancaria cuenta2 = new CuentaBancaria("1002", "Ana Gómez");
        CuentaBancaria cuenta3 = new CuentaBancaria();

        // Modificación de estado
        cuenta1.depositar(300.00);
        cuenta1.retirar(49.25);
        cuenta2.depositar(150.00);
        cuenta2.retirar(100.00);
        cuenta3.depositar(-20.00);          // inválido, no cambia el saldo
        boolean retiroOk = cuenta3.retirar(80.00); // 0.0 + 0.0 de crédito < 80, se rechaza

        // Reporte de vehículos
        System.out.printf("=== REPORTE DE VEHÍCULOS ===%n");
        for (int i = 0; i < vehiculos.length; i++) {
            Vehiculo v = vehiculos[i];
            System.out.printf("[%d] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                    i + 1, v.getMarca(), v.getModelo(), v.getKilometraje(), v.getVelocidadMaxima(), v.isEncendido());
        }

        // Reporte de estudiantes
        System.out.printf("%n=== REPORTE DE ESTUDIANTES ===%n");
        for (Estudiante e : estudiantes) {
            System.out.printf("%s%n", e.obtenerReporteAcademicos());
        }

        // Reporte de cuentas
        System.out.printf("%n=== REPORTE DE CUENTAS BANCARIAS ===%n");
        CuentaBancaria[] cuentas = {cuenta1, cuenta2, cuenta3};
        for (CuentaBancaria c : cuentas) {
            System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                    c.getNumeroCuenta(), c.getTitular(),
                    ConversorMoneda.formatearMoneda(c.getSaldo()),
                    ConversorMoneda.formatearMoneda(c.getLimiteCredito()));
        }
        System.out.printf("Retiro de $80.00 en cuenta %s aprobado: %b%n", cuenta3.getNumeroCuenta(), retiroOk);

        // Métodos estáticos llamados directamente desde la clase
        double euros = ConversorMoneda.convertirDolaresAEuros(250.00, 0.92);
        System.out.printf("%n250.00 USD equivalen a %s EUR%n", ConversorMoneda.formatearMoneda(euros));
    }
}
