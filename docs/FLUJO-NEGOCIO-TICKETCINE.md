# TicketCine — Flujo de negocio y arquitectura

**Alcance:** plataforma de venta de entradas de cine. Microservicios con BD PostgreSQL independiente, coordinados por REST, Feign, Kafka y Eureka. API Gateway en `:9000`, despliegue Docker con `run-dockers.bat`.

---

## 1. Visión general

TicketCine gestiona **películas en cartelera**, **inventario de boletos**, **compras**, **pagos** y **usuarios** con autenticación JWT.

| Mecanismo | Cuándo | Ejemplo |
|-----------|--------|---------|
| **REST** | Cliente → API | `POST /api/v1/compras` vía gateway |
| **Feign** | Respuesta inmediata entre MS | Compras pide precio a Boletos |
| **Kafka** | Eventos asíncronos | Catálogo publica película → Boletos actualiza proyección |
| **Eureka** | Descubrimiento | `lb://ms-compras` en el gateway |

**Infraestructura Docker:**

| Servicio | Puerto |
|----------|--------|
| API Gateway | 9000 |
| Eureka | 8761 |
| PostgreSQL | 5433 |
| Kafka | 9092 |
| Kafka UI | 8080 |

---

## 2. Mapa de microservicios

| Microservicio | Puerto | Base de datos | Rol |
|---------------|--------|---------------|-----|
| **eureka** | 8761 | — | Service discovery |
| **api-gateway** | 9000 | — | Entrada única HTTP |
| **ms-usuarios** | 9010 | `usuarios` | Auth JWT, usuarios, perfiles |
| **ms-catalogo** | 9003 | `catalogo` | Catálogo de películas (fuente de verdad) |
| **ms-boletos** | 9002 | `boletos` | Boletos, zonas, proyección local de películas |
| **ms-compras** | 9004 | `compras` | Carritos y órdenes de compra |
| **ms-pagos** | 9006 | `pagos` | Cobros y aprobación de pagos |
| **common** | — | — | JWT, excepciones, eventos Kafka |

**Credenciales seed:** `ana@administrador.cl` / `TicketCine@2026` (Administrador). Cliente de ejemplo: `carlos@cliente.cl`.

**Swagger UI (puerto directo, no gateway):**

| MS | URL |
|----|-----|
| ms-usuarios | http://localhost:9010/swagger-ui.html |
| ms-catalogo | http://localhost:9003/swagger-ui.html |
| ms-boletos | http://localhost:9002/swagger-ui.html |
| ms-compras | http://localhost:9004/swagger-ui.html |
| ms-pagos | http://localhost:9006/swagger-ui.html |

---

## 3. Datos semilla (`init-multi-db/`)

### Películas (`catalogo.Peliculas` ↔ `boletos.proy_Peliculas`)

| id | Película | Fecha |
|----|----------|-------|
| 1 | Dune: Parte Dos | 2025-12-20 |
| 2 | Oppenheimer | 2025-11-15 |
| 3 | Intensamente 2 | 2025-10-01 |
| 4 | Deadpool & Wolverine | 2026-03-15 |

### Boletos de ejemplo

| id_boleto | id_Pelicula | Código | Zona | Estado |
|-----------|-------------|--------|------|--------|
| 1 | 1 | TKT-LB-001 | Cancha General | Vendido |
| 2 | 1 | TKT-LB-002 | Cancha General | Vendido |
| 3 | 2 | TKT-DL-501 | Cancha General | Reservado |
| 4 | 2 | TKT-DL-502 | VIP Experience | Vendido |

### Compras de ejemplo

| id_compra | Usuario (seed) | Boletos | Total | Estado |
|-----------|----------------|---------|-------|--------|
| 1 | Carlos | 1 + 2 | $90.000 | COMPLETADA |
| 2 | Camila | 4 | $150.000 | COMPLETADA |
| 3 | Cristian | 3 | $45.000 | PENDIENTE |

> Los scripts de compras referencian `id_usuario` 7–9; con el seed actual de usuarios (10 filas) conviene verificar el id real de Carlos en BD antes de probar carritos.

---

## 4. Fase A — Preparación (back-office)

Orden lógico antes de que un cliente compre:

```
┌─────────────┐     POST /api/v1/Peliculas      ┌─────────────┐
│ ms-catalogo │ ──────────────────────────────► │   Kafka     │
│  (película) │   catalogo.Pelicula.created    │             │
└─────────────┘                                 └──────┬──────┘
                                                       ▼
                                                ┌─────────────┐
                                                │ ms-boletos  │
                                                │ proy_Peliculas│
                                                └──────┬──────┘
                                                       │
                                                POST /api/v1/boletos
                                                       ▼
                                                Kafka: boletos.boleto.created
                                                       ▼
                                                ms-compras.proy_boletos
                                                ms-usuarios.proy_boletos
```

1. **Organizador/Administrador** crea o actualiza películas en `ms-catalogo`.
2. Kafka replica datos mínimos en `ms-boletos.proy_Peliculas`.
3. **Organizador/Administrador** crea boletos en `ms-boletos` por película y zona.
4. Kafka actualiza proyecciones locales en compras y usuarios.

---

## 5. Fase B — Compra (cliente)

```
Cliente (JWT Cliente)
    │
    ├─► POST /api/v1/carritos/boletos/id/{id}     (ms-compras)
    ├─► POST /api/v1/compras                      (ms-compras) → Kafka compras.compra.created
    ├─► POST /api/v1/pagos                        (ms-pagos)    → Kafka pagos.pago.created
    └─► PUT  /api/v1/pagos/aprobar/id/{id}        (ms-pagos)    → Feign confirma compra + Kafka
```

**Feign en el flujo:**

- Compras → Boletos: precio del boleto (`GET /api/v1/boletos/precio/id/{id}`).
- Compras → Pagos: crear pago.
- Pagos → Compras: confirmar compra al aprobar.

---

## 6. Roles y permisos (resumen)

| Rol | Ejemplos de acceso |
|-----|-------------------|
| **Cliente** | GET Peliculas/boletos; carrito y compras propias |
| **Organizador** | GET usuarios; CRUD Peliculas y boletos; listar compras/pagos |
| **Administrador** | CRUD usuarios; todo lo anterior + eliminar pagos |

Detalle completo en [01-Seguridad-JJWT.md](./01-Seguridad-JJWT.md).

---

## 7. Topics Kafka

| Topic | Producer | Consumer(s) |
|-------|----------|-------------|
| `catalogo.Pelicula.created` | ms-catalogo | ms-boletos |
| `catalogo.Pelicula.updated` | ms-catalogo | ms-boletos |
| `catalogo.Pelicula.deleted` | ms-catalogo | ms-boletos |
| `boletos.boleto.created` | ms-boletos | ms-compras, ms-usuarios |
| `boletos.boleto.updated` | ms-boletos | ms-compras, ms-usuarios |
| `compras.compra.created` | ms-compras | ms-pagos |
| `compras.compra.updated` | ms-compras | ms-pagos |
| `pagos.pago.created` | ms-pagos | ms-compras |
| `pagos.pago.updated` | ms-pagos | ms-compras |

Más detalle en [KAFKA-IMPLEMENTACION.md](./KAFKA-IMPLEMENTACION.md).

---

## 8. Cómo probar el flujo completo

```bat
run-dockers.bat
```

1. Login: `POST :9000/api/v1/auth/login` con `carlos@cliente.cl` / `TicketCine@2026`.
2. Listar películas: `GET :9000/api/v1/Peliculas`.
3. Ver boleto disponible: `GET :9000/api/v1/boletos/id/3`.
4. Agregar al carrito y crear compra (carpeta **Flujo de compra** en Postman).
5. Crear y aprobar pago.

Colección: [`TicketCine.postman_collection.json`](./TicketCine.postman_collection.json).

---

## 9. Documentos relacionados

- [05-Api-Gateway-TicketCine.md](./05-Api-Gateway-TicketCine.md)
- [03-Documentación-con-Swagger.md](./03-Documentación-con-Swagger.md)
- [04-Implementación-HATEOAS.md](./04-Implementación-HATEOAS.md)
- [MEJORAS-OPCIONALES.md](./MEJORAS-OPCIONALES.md)

---

*TicketCine — venta de entradas de cine, arquitectura de microservicios.*
