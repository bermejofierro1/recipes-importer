# Uso de IA durante el desarrollo

Durante el desarrollo utilicé Codex/ChatGPT de forma puntual como herramienta de apoyo, principalmente para revisar el enfoque, contrastar algunas decisiones y detectar posibles puntos de mejora.

La implementación principal, la estructura del proyecto y las decisiones finales fueron realizadas por mí.

---

## Uso realizado

El uso de IA se centró en tareas concretas:

- Contrastar si el modelo `Recipe` + `Ingredient` era razonable para representar los datos de TheMealDB.
- Revisar posibles mejoras pequeñas sin sobredimensionar la solución.
- Ayudar a detectar un problema concreto en el mapeo de ingredientes.
- Revisar la claridad de la documentación final.

---

## Prompts representativos

Algunos prompts utilizados durante el proceso fueron:

```txt
Revisa este mapper y dime si ves algún problema en la transformación de ingredientes.
```

```txt
Ayúdame a revisar la redacción del README para que explique bien las decisiones técnicas.
```
---

## Decisiones contrastadas

Usé la IA para contrastar algunas decisiones, pero no para delegarlas completamente.

Las decisiones principales fueron:

- Mantener la aplicación como un importador ejecutado con `CommandLineRunner`.
- No añadir endpoints REST, ya que el enunciado pedía almacenar recetas, no construir una API.
- Usar H2 para facilitar la ejecución local.

---

## Ejemplo concreto de ayuda

Uno de los usos más útiles fue durante la revisión del test de `RecipeMapper`.

Al añadir un test unitario para comprobar el mapeo de ingredientes, se detectó que la medida y el nombre normalizado podían quedar cruzados por el orden de parámetros del constructor de `Ingredient`.

El test ayudó a corregir el problema y dejar el mapeo esperado:

```txt
name = Chicken Breast
measure = 200g
normalizedName = chicken breast
```

---

## Alcance del uso de IA

La IA se utilizó como apoyo para revisión, contraste y documentación. No se utilizó para generar una solución completa ni para sustituir el diseño del proyecto.

El objetivo fue mantener una solución sencilla, clara y ajustada al tiempo estimado del ejercicio, priorizando que el flujo principal estuviera bien resuelto antes que añadir funcionalidades innecesarias.
