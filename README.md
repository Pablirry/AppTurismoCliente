# AppTurismoCliente

Aplicación Android para la gestión y consulta de rutas, restaurantes, eventos y mensajes turísticos en la región de Asturias.

## Características

- Visualización de rutas turísticas.
- Consulta y valoración de restaurantes.
- Gestión de eventos y reservas.
- Historial de actividades del usuario.
- Sistema de mensajería interna.
- Integración con servicios de localización de Google.
- Interfaz moderna y adaptada a dispositivos móviles.

## Tecnologías utilizadas

- **Lenguaje:** Java
- **Framework:** Android SDK
- **Gestor de dependencias:** Gradle
- **Base de datos:** MySQL (conexión mediante `mysql-connector-java`)
- **Librerías principales:**
  - AndroidX (AppCompat, ConstraintLayout, RecyclerView, Lifecycle)
  - Material Components
  - Google Play Services Location

## Estructura del proyecto
AppTurismoCliente/ ├── app/ │ ├── src/ │ │ ├── main/ │ │ │ ├── java/com/example/AppTurismo/Activities/ │ │ │ ├── res/layout/ │ │ │ ├── res/drawable/ │ │ │ ├── res/mipmap/ │ │ │ ├── res/values/ │ │ │ └── AndroidManifest.xml │ │ ├── test/java/com/example/AppTurismo/ │ │ └── androidTest/java/com/example/AppTurismo/ │ ├── build.gradle ├── gradle.properties ├── gradlew ├── gradlew.bat └── settings.gradle
