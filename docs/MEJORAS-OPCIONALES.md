# TicketCine — Mejoras opcionales y roadmap

Documento de referencia para ampliar el proyecto. **No es obligatorio** para el funcionamiento actual.

---

## HATEOAS

| Microservicio | Estado | Mejora opcional |
|---------------|--------|-----------------|
| ms-catalogo (`PeliculaController`) | ✅ Implementado | HATEOAS en `POST` create; links condicionales por rol |
| ms-boletos (`BoletoController`) | ✅ Implementado | HATEOAS en `ProyPeliculaController` |
| ms-usuarios (`UsuarioController`) | ✅ Implementado | HATEOAS en `PerfilResponse` |
| ms-compras | Pendiente | Links en `CompraResponse` / carrito (confirmar, detalle) |
| ms-pagos | Pendiente | Link `aprobar` cuando estado sea PENDIENTE |

### Enriquecimiento de respuestas

- **ms-boletos:** incluir en `BoletoResponse` resumen de película (`nombrePelicula`, `fecha`) desde `proy_Peliculas`.
- **ms-usuarios:** incluir `perfil` embebido en `UsuarioResponse` al hacer GET por id.

### Endpoints sin HATEOAS (por diseño)

- `AuthController` — login/register/logout.
- `GET .../precio/id/{id}` — devuelve escalar.
- `GET .../existe/*` — utilitarios booleanos.
- Clientes Feign internos entre microservicios.

---

## Seguridad y acceso

- [x] **API Gateway** — rutas en [05-Api-Gateway-TicketCine.md](./05-Api-Gateway-TicketCine.md)
- [x] **Swagger en ms-catalogo y ms-usuarios** — anotaciones completas
- [ ] **Swagger en boletos, compras y pagos** — replicar patrón de `PeliculaController`
- [ ] **Validar usuario en compra** — Feign a `ms-usuarios` antes de guardar
- [ ] **Links HATEOAS según rol** — ocultar `delete`/`update` si el token no tiene permiso

---

## Flujo de negocio / Kafka

- [ ] **Marcar boleto Vendido al confirmar compra** — consumer o llamada desde `CompraService.confirmar`
- [ ] **Peliculas `pagos.pago.updated` para Fallido/Reembolsado** — ampliar `PagoService` más allá de `aprobar`

---

## Datos y consistencia

- [ ] **Alinear IDs de usuario en seed** — `04-compras.sql` asume Carlos = id 7; `10-usuarios.sql` inserta 10 usuarios (Carlos puede ser id 8)
- [ ] **Boletos para películas 3 y 4** — ampliar inventario en `02-boletos.sql`

---

## Calidad y operación

- [ ] Tests de integración HATEOAS — verificar `_links.self` en respuestas
- [ ] Tests de contrato Feign entre MS
- [ ] Corregir tests obsoletos en `PeliculaServiceTest` (referencias a artistas/recintos eliminados)

---

## Documentación

- [x] Gateway y Swagger alineados con TicketCine
- [x] Postman Collection sin MS inexistentes
- [ ] Guía de despliegue en producción (variables de entorno JWT, BD, Eureka)

---

*Última actualización: julio 2026 — revisar al implementar cada ítem.*
