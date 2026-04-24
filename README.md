# EMOTRACK AI

Backend universitario construido con Java 21, Spring Boot 4, Spring Data JPA, Spring Security, JWT y H2.

## Objetivo

EMOTRACK AI apoya la deteccion temprana de riesgos emocionales en estudiantes a partir de registros en texto libre.
No reemplaza la evaluacion profesional; sirve como apoyo institucional para priorizar casos y generar alertas.

## Stack

- Java 21
- Spring Boot 4
- Maven
- Spring Data JPA
- Spring Security
- JWT
- H2 Database
- Lombok
- Bean Validation

## Arquitectura

- `config`: configuraciones de seguridad y datos iniciales
- `controller`: API REST
- `dto`: contratos de entrada y salida
- `entity`: modelo relacional
- `repository`: acceso a datos
- `service`: logica de negocio
- `security`: JWT y carga de usuarios
- `exception`: manejo global de errores
- `enums`: catalogos del dominio
- `util`: mapeos auxiliares

## Ejecucion

```bash
./mvnw spring-boot:run
```

Consola H2:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:emotrackdb`
- User: `sa`
- Password: vacio

## Usuarios semilla

- Admin: `admin@emotrack.ai` / `Admin123*`
- Profesional: `profesional@emotrack.ai` / `Profesional123*`

## Endpoints principales

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET|POST|PUT|DELETE /api/registros`
- `GET /api/alertas`
- `POST /api/seguimientos`
- `GET|PUT|DELETE /api/usuarios`

## Configuracion PostgreSQL opcional

Se dejo `src/main/resources/application-postgres.yml` como referencia para cambiar a PostgreSQL si el profesor lo solicita.
