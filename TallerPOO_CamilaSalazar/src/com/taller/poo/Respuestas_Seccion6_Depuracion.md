# Sección 6: Respuestas de depuración

## Caso 1: NullPointerException

1. El compilador marca `variable estudiante1 might not have been initialized`. Como es una variable local, Java ni siquiera deja compilar. Si `estudiante1` fuera un atributo de clase (que arranca en `null`), el error sería una `NullPointerException` en tiempo de ejecución.
2. Porque `estudiante1` solo es una etiqueta que todavía no apunta a ningún objeto. Un método de instancia necesita un objeto real con sus datos, y aquí no existe ninguno.
3. Se agrega el `new` en la declaración:

```java
Estudiante estudiante1 = new Estudiante("E001", "Laura Martínez", 4.35, true);
```

## Caso 2: Método de instancia desde contexto estático

1. `non-static method encenderMotor() cannot be referenced from a static context`.
2. Un método `static` pertenece a la clase y se puede ejecutar sin que exista ningún objeto. Un método de instancia trabaja con el estado de un objeto concreto. Desde `main` no hay ningún objeto "actual" (no existe `this`), así que Java no sabe sobre cuál ejecutar `encenderMotor()`.
3. Dos correcciones válidas:
   - Hacer el método estático: `public static void encenderMotor() { ... }`.
   - Crear un objeto y llamarlo desde él: `GestorVehiculos gestor = new GestorVehiculos(); gestor.encenderMotor();`.

## Caso 3: Firma incorrecta de constructor

1. Un constructor se distingue porque tiene el nombre de la clase y no declara tipo de retorno. Al poner `void`, Java lo toma como un método normal que casualmente se llama igual que la clase.
2. Como no hay ningún constructor real, Java crea uno por defecto sin parámetros. Al hacer `new CuentaBancaria("A-101", 500.0)` busca un constructor `(String, double)` y no lo encuentra; reporta que el constructor no se puede aplicar a esos tipos porque solo existe el de cero argumentos.
3. Versión correcta:

```java
public CuentaBancaria(String numeroCuenta, double saldoInicial) {
    this.numeroCuenta = numeroCuenta;
    this.saldo = saldoInicial;
}
```

## Caso 4: Variable shadowing

1. Imprime `Velocidad Máxima: 0 | Encendido: false`. Los atributos nunca se asignaron, así que conservan sus valores por defecto (0 y false).
2. El sombreado ocurre cuando un parámetro o variable local tiene el mismo nombre que un atributo: dentro del constructor, el nombre se refiere al parámetro y "tapa" al atributo. Entonces `velocidadMaxima = velocidadMaxima;` solo le asigna el parámetro a sí mismo y el atributo del objeto queda intacto.
3. Corrección con `this`:

```java
public Vehiculo(int velocidadMaxima, boolean encendido) {
    this.velocidadMaxima = velocidadMaxima;
    this.encendido = encendido;
}
```
