# Room714 Recipes Importer

Proyecto realizado para la prueba técnica de Room 714.

La aplicación lee un fichero de texto con nombres de recetas, consulta la API de TheMealDB, transforma los datos recibidos y guarda la información en una base de datos H2.

He intentado centrarme en resolver lo que se pedía en el ejercicio sin añadir funcionalidades extra que no eran necesarias, como una API REST propia o una interfaz web.

---

## Tecnologías usadas

* Java 17
* Spring Boot
* Maven
* Spring Data JPA
* H2 Database
* WebClient
* JUnit 5

He usado H2 porque permite ejecutar el proyecto fácilmente sin instalar una base de datos externa. Si el proyecto creciera, una opción que valoraría sería usar MongoDB, ya que las recetas encajan bastante bien con una estructura documental.

---

## Funcionamiento

Al arrancar la aplicación se ejecuta un `CommandLineRunner` que lanza el proceso de importación.

El flujo es el siguiente:

1. Se lee el fichero `recipes.txt`.
2. Cada línea del fichero se usa como nombre de receta a buscar.
3. Se consulta TheMealDB con `/search.php?s={nombre}`.
4. Se transforman los datos recibidos.
5. Se guardan recetas, ingredientes y etiquetas en H2.
6. Se muestra un resumen por logs.

Ejemplo del fichero `recipes.txt`:

```txt
Arrabiata
Chicken
Beef Wellington
Spaghetti
Salmon
Pad Thai
Pancakes
Shawarma
Gazpacho
xyzrecipethatdoesntexist
```

He dejado una receta que no existe para comprobar que el proceso no se rompe cuando la API no devuelve resultados.

---

## Modelo de datos

### Recipe

Entidad principal de receta.

Campos principales:

* `id`
* `mealDbId`
* `name`
* `category`
* `area`
* `instructions`
* `imageUrl`
* `youtubeUrl`
* `sourceUrl`
* `tags`
* `ingredients`

Uso `mealDbId` para evitar guardar la misma receta más de una vez si se ejecuta la aplicación varias veces.

### Ingredient

Entidad que representa cada ingrediente de una receta.

Campos principales:

* `id`
* `name`
* `measure`
* `normalizedName`
* `recipe`

TheMealDB devuelve los ingredientes en campos separados como `strIngredient1`, `strIngredient2`, `strMeasure1`, etc. En el proyecto los transformo a una lista de ingredientes para que sea más fácil trabajar con ellos.

También guardo `normalizedName`, que es una versión del nombre en minúsculas y sin espacios duplicados. Lo he añadido pensando en una posible comparación futura con productos de un carrito.

### Tags

TheMealDB devuelve las etiquetas como texto separado por comas, por ejemplo:

```txt
Pasta,Curry
```

En el proyecto las convierto a una lista y las guardo con `@ElementCollection`, para que no queden como un único texto y puedan consultarse mejor.

---

## Transformaciones realizadas

Las transformaciones principales son:

* Quitar espacios innecesarios.
* Ignorar ingredientes vacíos.
* Convertir los ingredientes numerados de TheMealDB a una lista.
* Crear un `normalizedName` para los ingredientes.
* Separar los tags en una lista.
* Guardar solo los campos que considero útiles para esta primera versión.

Ejemplo:

```txt
"  Chicken   Breast "
```

Se guarda como:

```txt
name = "Chicken Breast"
normalizedName = "chicken breast"
```

---

## Manejo de errores

El proceso intenta continuar aunque haya errores puntuales.

Casos contemplados:

* Si una búsqueda no devuelve resultados, se registra en logs y se continúa.
* Si hay un error consultando TheMealDB, se registra el error.
* Si el fichero no se puede leer, se lanza una excepción propia.
* Si una receta ya existe en base de datos, no se vuelve a guardar.

La idea es que una receta concreta no detenga toda la importación.

---

## Configuración

La configuración principal está en `application.properties`.

Valores más importantes:

```properties
spring.datasource.url=jdbc:h2:file:./data/recipesdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

app.themealdb.base-url=https://www.themealdb.com/api/json/v1/1
app.themealdb.timeout-seconds=10
app.recipes.input-file=recipes.txt
```

La URL de TheMealDB, el timeout y el nombre del fichero están en configuración para no dejarlos fijos en el código.

---

## Cómo ejecutar

En Windows:

```bash
mvnw.cmd spring-boot:run
```

En Linux o Mac:

```bash
./mvnw spring-boot:run
```

Al terminar, se muestra un resumen parecido a:

```txt
Recetas guardadas: 51
Recetas duplicadas: 0
Búsquedas sin resultado: 1
```

Si se ejecuta otra vez sin borrar la base de datos, las recetas aparecerán como duplicadas porque ya están guardadas.

---

## Consola H2

Mientras la aplicación está arrancada se puede acceder a:

```txt
http://localhost:8080/h2-console
```

Datos de conexión:

```txt
JDBC URL: jdbc:h2:file:./data/recipesdb
User: sa
Password:
```

Algunas consultas útiles:

```sql
SELECT COUNT(*) FROM RECIPE;
```

```sql
SELECT COUNT(*) FROM INGREDIENT;
```

```sql
SELECT * FROM RECIPE_TAGS;
```

```sql
SELECT r.NAME, i.NAME, i.MEASURE, i.NORMALIZED_NAME
FROM RECIPE r
JOIN INGREDIENT i ON r.ID = i.RECIPE_ID
WHERE r.NAME = 'Spicy Arrabiata Penne';
```

---

## Tests

He añadido tests unitarios sobre `RecipeMapper`, porque es donde está la parte más importante de transformación de datos.

Para ejecutar los tests:

En Windows:

```bash
mvnw.cmd test
```

En Linux o Mac:

```bash
./mvnw test
```

Los tests comprueban principalmente:

* Que se limpian campos de texto.
* Que no se añaden ingredientes vacíos.
* Que se transforman ingredientes y medidas.
* Que se genera `normalizedName`.
* Que se separan correctamente los tags.

---

## Estructura del proyecto

```txt
src/main/java/com/miguelbermejo/roomrecipes
├── client        # Cliente para consultar TheMealDB
├── config        # Configuración de WebClient
├── dto           # DTOs para recibir y mover datos
├── exception     # Excepciones propias
├── mapper        # Transformación entre DTOs y entidades
├── model         # Entidades JPA
├── repositorio   # Repositorio de recetas
├── runner        # Punto de arranque del importador
└── service       # Servicios de lectura e importación
```

---

## Decisiones tomadas

Algunas decisiones que he tomado durante el desarrollo:

* He usado `CommandLineRunner` porque el ejercicio pedía un importador, no una API REST.
* He usado `WebClient` para consultar TheMealDB.
* He usado H2 para facilitar la ejecución local.
* He separado el código en cliente, servicio, mapper, modelo y repositorio para que cada parte tenga una responsabilidad clara.
* He transformado los ingredientes a una lista propia en lugar de guardar los campos planos de la API.
* He añadido `normalizedName` pensando en una posible comparación futura con productos de un carrito.
* He usado `mealDbId` para controlar duplicados.

---

## Mejoras que haría con más tiempo

Si tuviera más tiempo o el proyecto continuara, mejoraría estas partes:

* Añadir más tests para el servicio de importación.
* Añadir tests de integración.
* Permitir indicar el fichero de entrada por parámetro.
* Mejorar la normalización de ingredientes con un catálogo de productos.
* Añadir índices a campos como `mealDbId` y `normalizedName`.
* Migrar la base de datos a MongoDB.
* Añadir una API REST sencilla para consultar las recetas guardadas.
* Añadir reintentos si TheMealDB falla temporalmente.
* Procesar el fichero en lotes si fuese muy grande.

---

## Uso de IA

La explicación sobre el uso de IA durante el desarrollo está incluida en el fichero `USO_IA.md`.
