# Sistema PQRS - Peticiones, Quejas, Reclamos y Sugerencias

Sistema web para la gestión y seguimiento de solicitudes PQRS (Peticiones, Quejas, Reclamos y Sugerencias).

## Estructura del Proyecto

```
pqrs_back/    # Backend (Spring Boot, Java)
pqrs_front/   # Frontend (HTML, JS, TailwindCSS)
.vscode/      # Configuración de VS Code
.idea/        # Configuración de IntelliJ IDEA
README.md     # Este archivo
```

## Características

- Registro y autenticación de usuarios
- Envío de solicitudes PQRS
- Consulta de estado por número de radicado
- Panel de administración y reportes
- Gestión de usuarios y roles
- Estadísticas y tiempos de respuesta

## Tecnologías

- **Backend:** Java, Spring Boot, JPA, JWT, PostgreSQL, Thymeleaf
- **Frontend:** HTML, JavaScript, TailwindCSS

## Instalación

### Backend

1. Ve a la carpeta `pqrs_back`
2. Instala dependencias y ejecuta el proyecto:

```sh
./mvnw spring-boot:run
```

3. Configura la base de datos en `application.properties` si es necesario.

### Frontend

1. Ve a la carpeta `pqrs_front/src`
2. Abre los archivos HTML en tu navegador o sirve la carpeta con un servidor estático.

## Uso

- Accede a la página principal para enviar PQRS o consultar estado.
- Los administradores pueden acceder al dashboard y reportes.
- El sistema notifica por correo electrónico el estado de la solicitud.

## Licencia

Este proyecto está bajo la licencia Apache 2.0.

---