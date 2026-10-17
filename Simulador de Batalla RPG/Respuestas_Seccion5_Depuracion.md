# Sección 5: Respuestas de depuración de encapsulamiento

## Caso A: Acceso directo no autorizado

**Error del compilador:** `puntosVida has private access in co.edu.puj.rpg.modelo.Personaje`.

**Causa raíz:** el atributo es `private`, así que solo se puede tocar desde dentro de la propia clase `Personaje`. `Main` está en otra clase (y otro paquete), por eso Java le cierra el paso en `heroe.puntosVida = 200` y también en la lectura `heroe.puntosVida`.

**Corrección:** usar la API pública.

```java
heroe.setPuntosVida(200);                       // el setter lo limita a vidaMaxima (100)
System.out.println("Vida: " + heroe.getPuntosVida());
```

## Caso B: Bypass de validaciones en el constructor

**Problema:** el constructor asigna `this.puntosVida = -50` directamente, saltándose las reglas del setter, y deja `estaVivo = true`. Queda un objeto incoherente: vida negativa pero "vivo".

**Causa raíz:** la validación solo existe en el setter, y el constructor no la usa. Cualquier ruta que escriba el atributo sin pasar por el setter puede romper las reglas de negocio.

**Corrección:** que el constructor delegue en el setter, así la regla vive en un solo lugar.

```java
public Personaje(int puntosVida) {
    this.vidaMaxima = 100;
    setPuntosVida(puntosVida);   // -50 se convierte en 0 y estaVivo queda en false
}
```

(Ojo: `vidaMaxima` debe asignarse antes de llamar al setter, porque este la usa para el tope.)

## Caso C: Fuga de estado y valores fuera de límites

**Problema:** `setPuntosVida(999999)` y `setPuntosVida(-100)` serían peligrosos si el setter fuera un simple `this.puntosVida = vida;`: la vida pasaría de `vidaMaxima`, o quedaría negativa con `estaVivo` todavía en `true`.

**Causa raíz:** un setter sin validación no protege nada; tener el atributo `private` no sirve de mucho si el método público deja pasar cualquier valor.

**Corrección (la que lleva el proyecto):**

```java
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
```

Con esto, `999999` queda en 100 (el tope) y `-100` queda en 0 con `estaVivo = false`.

## Guía de verificación 6.2 (IntelliJ)

1. En `Main`, escribe `heroe.` y mira el autocompletado: solo debe mostrar métodos `public` (getters, setters, `atacar`, `usarPocion`, `recibirDano`, `ganarExperiencia`), ningún campo.
2. Intenta `heroe.puntosVida = 200;` y verás el error `'puntosVida' has private access in 'co.edu.puj.rpg.modelo.Personaje'`.
3. Ejecuta `PruebaLimites.java`: con -10 y 0 la vida queda en 0 y `vivo: false`; con 50 queda en 50; con 100000 queda en 100. En oro, -10 se rechaza y el saldo no cambia.
