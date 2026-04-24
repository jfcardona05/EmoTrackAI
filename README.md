# EMOTRACK AI

Backend universitario para deteccion temprana de riesgos de salud mental estudiantil.

## Stack

- Java 21
- Spring Boot 4
- Maven
- H2 Database
- Spring Data JPA
- Spring Security
- JWT
- Lombok

## Ejecucion

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Credenciales demo

- Admin: `admin@emotrack.ai` / `Admin123*`
- Profesional: `profesional@emotrack.ai` / `Profesional123*`

## Base de datos

Por defecto el proyecto usa H2 en memoria para ahorrar tiempo en desarrollo:

- URL aplicacion: `http://localhost:8080`
- Consola H2: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:emotrackdb`
- User: `sa`

Si necesitan PostgreSQL, ya existe `application-postgres.yml`.

## Endpoints principales

### Auth

- `POST /api/auth/register`
- `POST /api/auth/login`

### Usuarios

- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`

### Registros emocionales

- `POST /api/registros`
- `GET /api/registros`
- `GET /api/registros/{id}`
- `PUT /api/registros/{id}`
- `DELETE /api/registros/{id}`

### Alertas

- `GET /api/alertas`
- `GET /api/alertas/{id}`

### Seguimientos

- `POST /api/seguimientos`
- `GET /api/seguimientos`
- `GET /api/seguimientos/{id}`
- `PUT /api/seguimientos/{id}`
- `DELETE /api/seguimientos/{id}`
