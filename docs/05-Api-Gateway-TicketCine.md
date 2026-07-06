# API Gateway — TicketCine

Documento de referencia del **Spring Cloud Gateway** en TicketCine: punto de entrada único para todos los microservicios de negocio.

---

## 1. Rol del gateway

| Sin gateway | Con gateway (`:9000`) |
|-------------|------------------------|
| El cliente conoce el puerto de cada MS | Un solo host/puerto para toda la API |
| CORS y rutas duplicadas por MS | CORS centralizado en el gateway |
| Sin balanceo unificado | `lb://` + Eureka distribuye entre instancias |

**Regla de oro:** los clientes (Postman, frontend, apps) deben usar `http://localhost:9000` en desarrollo. Swagger UI sigue en el puerto directo de cada MS (el gateway es WebFlux; SpringDoc servlet no aplica).

---

## 2. Arquitectura

```
                         ┌─────────────────┐
                         │ Eureka :8761    │
                         └────────┬────────┘
                                  │
Cliente ──► API Gateway :9000 ────┼──► ms-usuarios   :9010  auth, usuarios
                                  ├──► ms-boletos    :9002  boletos, proyPeliculas
                                  ├──► ms-catalogo   :9003  Peliculas
                                  ├──► ms-compras    :9004  compras, carritos
                                  └──► ms-pagos      :9006  pagos
```

**No pasa por el gateway:**

- **Feign** entre MS (ej. compras → boletos/pagos): HTTP vía Eureka.
- **Kafka**: bus de eventos asíncronos.
- **Swagger UI**: `http://localhost:9010/swagger-ui.html`, `:9003/swagger-ui.html`, etc.

---

## 3. Rutas configuradas (`api-gateway/application.yml`)

| ID ruta | Servicio Eureka | Path | Puerto Docker |
|---------|-----------------|------|---------------|
| `ms-usuarios-auth` | ms-usuarios | `/api/v1/auth/**` | 9010 |
| `ms-usuarios` | ms-usuarios | `/api/v1/usuarios/**` | 9010 |
| `ms-catalogo` | ms-catalogo | `/api/v1/Peliculas/**` | 9003 |
| `ms-boletos` | ms-boletos | `/api/v1/boletos/**` | 9002 |
| `ms-boletos-proyeccion` | ms-boletos | `/api/v1/proyPeliculas/**` | 9002 |
| `ms-compras` | ms-compras | `/api/v1/compras/**` | 9004 |
| `ms-compras-carritos` | ms-compras | `/api/v1/carritos/**` | 9004 |
| `ms-pagos` | ms-pagos | `/api/v1/pagos/**` | 9006 |

Los paths **no se reescriben**: `GET :9000/api/v1/Peliculas` llega al MS como `/api/v1/Peliculas`.

Rutas separadas en el mismo MS (boletos vs. proyPeliculas; compras vs. carritos) facilitan lectura de logs y `actuator/gateway/routes`.

---

## 4. Cómo arrancar

### Stack completo con Docker

```bat
run-dockers.bat
```

Levanta PostgreSQL, Kafka, Eureka, gateway y los 5 microservicios.

### Solo el gateway (MS ya en Eureka)

```bat
mvn -f api-gateway spring-boot:run
```

### Verificación

| Recurso | URL |
|---------|-----|
| Eureka | http://localhost:8761 |
| Gateway health | http://localhost:9000/actuator/health |
| Rutas activas | http://localhost:9000/actuator/gateway/routes |

---

## 5. Prueba E2E vía gateway

Colección Postman: [`TicketCine.postman_collection.json`](./TicketCine.postman_collection.json)  
Variable: `gateway_url` = `http://localhost:9000`

```http
# 1. Login (público)
POST http://localhost:9000/api/v1/auth/login
Content-Type: application/json

{
  "correo": "ana@administrador.cl",
  "password": "TicketCine@2026"
}

# 2. Listar películas (JWT)
GET http://localhost:9000/api/v1/Peliculas
Authorization: Bearer <token>

# 3. Consultar boleto
GET http://localhost:9000/api/v1/boletos/id/1
Authorization: Bearer <token>

# 4. Crear compra
POST http://localhost:9000/api/v1/compras
Authorization: Bearer <token>
Content-Type: application/json

# 5. Aprobar pago
PUT http://localhost:9000/api/v1/pagos/aprobar/id/1
Authorization: Bearer <token>
```

---

## 6. CORS y monitoreo

**CORS** (en `application.yml` del gateway): orígenes `localhost:3000`, `4200`, `5173` para frontends en desarrollo.

**Actuator:**

| Endpoint | Uso |
|----------|-----|
| `GET /actuator/health` | Estado del gateway |
| `GET /actuator/gateway/routes` | Lista de rutas y predicados |
| `GET /actuator/info` | Metadatos de la app |

---

## 7. Troubleshooting

| Síntoma | Causa habitual | Qué revisar |
|---------|----------------|-------------|
| 404 en gateway | Path incorrecto | `/api/v1/Peliculas` (no `/libros`); `actuator/gateway/routes` |
| 503 Service Unavailable | MS no en Eureka | Dashboard `:8761` |
| 401 en ruta protegida | Sin token o token inválido | Login vía `:9000/api/v1/auth/login` |
| 500 en login | Credenciales o usuario inexistente | Seed: `ana@administrador.cl` / `TicketCine@2026` |
| Hostname ilegible en Eureka | Windows `*.mshome.net` | `prefer-ip-address: true` + `hostname: localhost` en cada MS |

---

## 8. Documentos relacionados

- [FLUJO-NEGOCIO-TICKETCINE.md](./FLUJO-NEGOCIO-TICKETCINE.md) — flujo de compra y roles
- [01-Seguridad-JJWT.md](./01-Seguridad-JJWT.md) — JWT y matriz de permisos
- [03-Documentación-con-Swagger.md](./03-Documentación-con-Swagger.md) — Swagger por MS (puerto directo)
- [MEJORAS-OPCIONALES.md](./MEJORAS-OPCIONALES.md) — extensiones futuras

---

*TicketCine — Java 21, Spring Boot 3.5, Spring Cloud Gateway, Eureka.*
