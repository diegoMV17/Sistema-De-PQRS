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
- Envío y gestión de solicitudes PQRS
- Consulta y cambio de estado por número de radicado o ID
- Panel de administración y reportes dinámicos
- Gestión de usuarios y roles
- Estadísticas, métricas y tiempos de respuesta
- Notificaciones automáticas y actualizadas en tiempo real
- Buscador avanzado en dashboard y reportes (por palabras, mayúsculas/minúsculas, tildes)
- Visualización gráfica interactiva de métricas

## Tecnologías

- **Backend:** Java, Spring Boot, JPA, JWT, PostgreSQL, Thymeleaf
- **Frontend:** HTML, JavaScript, TailwindCSS, Chart.js

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

## Lógica del Proyecto

### Backend
- **Autenticación:** Usuarios y roles gestionados con JWT y Spring Security.
- **PQRS:** CRUD completo, cambio de estado, historial, notificaciones automáticas, filtrado por estado, radicado y documento.
- **Estados:** CRUD de estados, búsqueda por nombre e ID.
- **Notificaciones:** Cálculo de PQRS nuevas y próximas a vencer, envío de notificaciones en tiempo real.
- **Reportes:** Endpoints para métricas y estadísticas de PQRS.
- **Validaciones:** Validación de datos en entidades y controladores.
- **Persistencia:** Uso de JPA y PostgreSQL para almacenamiento.

### Frontend
- **Dashboard:** Visualización de PQRS, cambio de estado, notificaciones en tiempo real, buscador avanzado.
- **Reportes:** Gráficos interactivos de métricas, filtrado dinámico por palabras, leyendas coloridas y animaciones.
- **Buscador:** Búsqueda por palabras completas, mayúsculas/minúsculas y tildes en dashboard y reportes.
- **Notificaciones:** Actualización automática al cambiar estados o recibir nuevas PQRS.
- **Interfaz:** Diseño responsivo y moderno con TailwindCSS.

### Endpoints Principales

#### PQRS
- **POST /api/pqrs**: Crea una nueva PQRS.
- **GET /api/pqrs**: Lista todas las PQRS.
- **GET /api/pqrs/{id}**: Obtiene una PQRS por su ID.
- **GET /api/pqrs/buscarPorRadicado/{numeroRadicado}**: Busca PQRS por número de radicado.
- **GET /api/pqrs/buscarPorDocumento/{numeroDocumento}**: Busca PQRS por número de documento.
- **DELETE /api/pqrs/eliminar/{id}**: Elimina una PQRS por su ID.
- **GET /api/pqrs/total**: Devuelve el número total de PQRS.
- **GET /api/pqrs/estado/{id}**: Devuelve el estado de una PQRS por ID.
- **GET /api/pqrs/estado/radicado/{numeroRadicado}**: Devuelve el estado por número de radicado.
- **GET /api/pqrs/porEstado/{estado}**: Lista PQRS por estado.
- **GET /api/pqrs/historial**: Devuelve el historial de PQRS.
- **GET /api/pqrs/notificaciones**: Devuelve notificaciones de PQRS (nuevas y próximas a vencer).
- **PATCH /api/pqrs/{id}/estado**: Cambia el estado de una PQRS por ID.
- **PATCH /api/pqrs/estado/radicado/{numeroRadicado}**: Cambia el estado por número de radicado.

#### Estados PQRS
- **POST /api/estados**: Crea un nuevo estado.
- **GET /api/estados**: Lista todos los estados.
- **GET /api/estados/{id}**: Busca estado por ID.
- **GET /api/estados/buscar/{nombre}**: Busca estado por nombre.
- **PUT /api/estados/{id}**: Actualiza estado por ID.
- **DELETE /api/estados/{id}**: Elimina estado por ID.

## Uso

- Accede a la página principal para enviar PQRS o consultar estado.
- Los administradores pueden acceder al dashboard y reportes.
- El sistema notifica por correo electrónico el estado de la solicitud.
- El dashboard y reportes se actualizan automáticamente y permiten búsquedas avanzadas.

## Licencia

Este proyecto está bajo la licencia Apache 2.0.

---