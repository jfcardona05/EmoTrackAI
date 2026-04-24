# Runbook EMOTRACK AI

## Arranque local

1. Ejecutar `.\mvnw.cmd spring-boot:run`
2. Verificar `http://localhost:8080`
3. Abrir `http://localhost:8080/h2-console`

## Flujo minimo

1. Registrar estudiante en `POST /api/auth/register`
2. Hacer login en `POST /api/auth/login`
3. Copiar token JWT
4. Enviar registro emocional en `POST /api/registros`
5. Consultar alertas en `GET /api/alertas`
6. Crear seguimiento en `POST /api/seguimientos`

## Notas

- El sistema no reemplaza psicologos.
- H2 es temporal para desarrollo rapido.
- Existe perfil `postgres` si luego necesitan migrar.
