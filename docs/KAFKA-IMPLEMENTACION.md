# Implementación Kafka en TicketCine

**Fecha:** 24 de junio de 2026  
**Alcance:** Completar la arquitectura event-driven parcialmente implementada en el proyecto.

---

## 1. Contexto: qué problema resuelve Kafka aquí

TicketCine es un sistema de **microservicios** donde cada servicio tiene su **propia base de datos**. Eso significa que `ms-compras` no puede hacer un `JOIN` directo contra la tabla `boletos` de `ms-boletos`, ni `ms-pagos` contra `compras` de `ms-compras`.

La solución adoptada en este proyecto es el patrón **Event-Driven Architecture (EDA)** con Kafka:

1. Cuando algo importante ocurre en un servicio (origen), se **publica un Pelicula**.
2. Los servicios que necesitan una copia local de esos datos (destino) **escuchan** el Pelicula y actualizan una **tabla de proyección** (`proy_*`).

Así cada microservicio mantiene solo los datos mínimos que necesita para operar, sin acoplarse a la base de datos de otro.

---

## 2. Estado anterior vs. estado actual

| Componente | Antes | Ahora |
|------------|-------|-------|
| Peliculas de catálogo → boletos | ✅ Funcionaba | ✅ Sin cambios |
| Peliculas de boletos → otros servicios | ❌ Producer existía pero **no se llamaba** | ✅ Conectado en `BoletoService` |
| Peliculas de compras → pagos/notificaciones/reportes | ❌ No existía | ✅ Implementado |
| Peliculas de pagos → compras/reportes | ❌ No existía | ✅ Implementado |
| Tablas `proy_*` en servicios destino | ⚠️ Solo en SQL, sin sincronización automática | ✅ Sincronizadas vía Kafka |

---

## 3. Mapa completo de Peliculas

```
ms-catalogo  ──catalogo.Pelicula.{created|updated|deleted}──►  ms-boletos
ms-boletos   ──boletos.boleto.{created|updated}────────────►  ms-compras, ms-precios, ms-usuarios
ms-compras   ──compras.compra.{created|updated}────────────►  ms-pagos, ms-notificaciones, ms-reportes
ms-pagos     ──pagos.pago.{created|updated}────────────────►  ms-compras, ms-reportes
```

### Topics Kafka creados o utilizados

|            Topic          |  Productor  |             Consumidores                 |
| ------------------------- | ----------- | ---------------------------------------- |
| `catalogo.Pelicula.created` | ms-catalogo | ms-boletos                               |
| `catalogo.Pelicula.updated` | ms-catalogo | ms-boletos                               |
| `catalogo.Pelicula.deleted` | ms-catalogo | ms-boletos                               |
| `boletos.boleto.created`  | ms-boletos  | ms-compras, ms-precios, ms-usuarios      |
| `boletos.boleto.updated`  | ms-boletos  | ms-compras, ms-precios, ms-usuarios      |
| `compras.compra.created`  | ms-compras  | ms-pagos, ms-notificaciones, ms-reportes |
| `compras.compra.updated`  | ms-compras  | ms-pagos, ms-notificaciones, ms-reportes |
| `pagos.pago.created`      | ms-pagos    | ms-compras, ms-reportes                  |
| `pagos.pago.updated`      | ms-pagos    | ms-compras, ms-reportes                  |

---

## 4. Cambios realizados (detalle por módulo)

### 4.1 Módulo `common` — Contratos de Peliculas compartidos

**Archivos nuevos:**

- `CompraEvent.java`, `CompraCreatedEvent.java`, `CompraUpdatedEvent.java`
- `PagoEvent.java`, `PagoCreatedEvent.java`, `PagoUpdatedEvent.java`

**Por qué:** Los Peliculas viven en `common` porque varios microservicios los producen o consumen. Un DTO compartido garantiza que el JSON que publica `ms-compras` sea exactamente el que deserializa `ms-pagos`.

**Ejemplo de payload (`CompraCreatedEvent`):**

```json
{
  "idCompra": 42,
  "idUsuario": 7,
  "total": 90000.00,
  "estado": "PENDIENTE"
}
```

---

### 4.2 `ms-boletos` — Conectar el producer que ya existía

**Archivo modificado:** `BoletoService.java`

**Qué se hizo:** Se inyectó `BoletoEventProducer` y se llama en:

- `create()` → publica `boletos.boleto.created`
- `update()` → publica `boletos.boleto.updated`

**Por qué:** El producer (`BoletoEventProducer`) y los topics (`KafkaTopicConfig`) ya existían, pero el servicio tenía el comentario `// FALTA APLICAR LOS PeliculaS DEL KAFKA`. Sin esta conexión, crear o actualizar un boleto **no notificaba a nadie**.

**Código relevante (conceptual):**

```java
// Después de guardar un boleto nuevo:
boletoEventProducer.sendCreated(BoletoCreatedEvent.builder()
    .idBoleto(boleto.getIdBoleto())
    .idPelicula(boleto.getPelicula().getIdPelicula())
    .idZona(boleto.getZona().getIdZona())
    .codigo(boleto.getCodigo())
    .estado(boleto.getEstado())
    .build());
```

---

### 4.3 `ms-compras`, `ms-precios`, `ms-usuarios` — Consumir Peliculas de boletos

**Qué se hizo en cada uno:**

|       Elemento         |                            Descripción                                        |
| ---------------------- | ----------------------------------------------------------------------------- |
| `ProyBoleto` (entidad) | Se agregaron campos `idPelicula` e `idZona` para alinear con el SQL y el Pelicula |
| `ProyBoletoRepository` | Repositorio JPA para la tabla `proy_boletos`                                  |
| `ProyBoletoService`    | Lógica de upsert (crear o actualizar proyección)                              |
| `BoletoEventConsumer`  | `@KafkaListener` en topics `boletos.boleto.created` y `.updated`              |

**Por qué:** Cuando un usuario agrega un boleto al carrito, `ms-compras` necesita saber si ese boleto existe y cuál es su estado (`Disponible`, `Reservado`, `Vendido`) **sin llamar por HTTP en cada operación**. La proyección local responde esa pregunta al instante.

---

### 4.4 `ms-compras` — Producer de compras y consumer de pagos

**Archivos nuevos:**

- `CompraEventProducer.java` — publica en `compras.compra.*`
- `KafkaTopicConfig.java` — declara los topics de compras
- `PagoEventConsumer.java` — escucha `pagos.pago.*`
- `ProyPagoRepository`, `ProyPagoService` — sincroniza tabla `proy_pagos`

**Archivo modificado:** `CompraService.java` — emite `CompraCreatedEvent` al guardar una compra.

**Por qué:**

- **Producer:** Cuando se crea una compra, pagos, notificaciones y reportes deben enterarse sin que compras conozca sus APIs internas.
- **Consumer de pagos:** Compras mantiene una proyección de pagos para validar si una compra pendiente ya fue pagada, sin consultar `ms-pagos` en cada request.

---

### 4.5 `ms-pagos` — Producer de pagos y consumer de compras

**Archivos nuevos:**

- `PagoEventProducer.java`, `KafkaTopicConfig.java`
- `CompraEventConsumer.java`
- `ProyCompraRepository`, `ProyCompraService`

**Archivo modificado:** `PagoService.java` — emite `PagoCreatedEvent` al guardar un pago.

**Por qué:** Pagos necesita datos mínimos de la compra (`total`, `estado`) para procesar el cobro. En lugar de depender solo de Feign (síncrono), la proyección `proy_compras` se actualiza automáticamente cuando compras publica un Pelicula.

---

### 4.6 `ms-notificaciones` y `ms-reportes` — No implementados

Estos módulos **no existen** en el código actual de TicketCine. Los escenarios que los mencionan son mejoras futuras (ver [MEJORAS-OPCIONALES.md](./MEJORAS-OPCIONALES.md)).

---

## 5. Escenarios de ejemplo

### Escenario A — Nueva película en el catálogo

**Actores:** Administrador del sistema  
**Flujo:**

1. El admin crea la película *"Dune: Parte Dos"* en `ms-catalogo`.
2. `PeliculaService` guarda el Pelicula y llama a `PeliculaEventProducer.sendCreated()`.
3. Kafka publica en `catalogo.Pelicula.created`.
4. `ms-boletos` recibe el Pelicula en `PeliculaEventConsumer.onPeliculaCreated()`.
5. `PeliculaProyeccionService` crea/actualiza un registro en `proy_Peliculas` (tabla local de boletos).

**Utilidad:** Boletos puede emitir tickets para esa función **sin consultar catálogo por HTTP** cada vez.

```
[Admin] → POST /api/v1/Peliculas (catalogo)
              ↓
         Kafka: catalogo.Pelicula.created
              ↓
         ms-boletos.proy_Peliculas ← fila idPelicula=1 (ej. seed)
```

> **Datos semilla:** Los scripts `init-multi-db/03-catalogo.sql` y `02-boletos.sql` ya incluyen 4 Peliculas alineados (ids 1–4). Tras recrear las BDD, `proy_Peliculas` coincide con `catalogo.Peliculas` sin pasar por Kafka.

---

### Escenario B — Se genera inventario de boletos (cambio principal de esta implementación)

**Actores:** Operador de boletos  
**Flujo:**

1. Se crean boletos para la película 1 (Dune) o 2 (Oppenheimer) en `ms-boletos`.
2. Por cada boleto, `BoletoService.create()` publica `boletos.boleto.created`.
3. Consumidores: **ms-compras** y **ms-usuarios** actualizan `proy_boletos`.

**Utilidad:** Al agregar el boleto **#3** (`TKT-DL-501`, Oppenheimer) al carrito, compras valida contra su proyección local.

**Seed de referencia:**

| id_boleto | id_Pelicula | Código | Estado |
|-----------|-----------|--------|--------|
| 1 | 1 | TKT-LB-001 | Vendido |
| 2 | 1 | TKT-LB-002 | Vendido |
| 3 | 2 | TKT-DL-501 | Reservado |
| 4 | 2 | TKT-DL-502 | Vendido |

```
[Operador] → POST /api/v1/boletos (boletos) × 500
                 ↓
            Kafka: boletos.boleto.created (×500)
                 ↓
    ┌────────────┼────────────┐
    ↓            ↓
 compras     usuarios
 proy_boletos proy_boletos
```

---

### Escenario C — Un cliente realiza una compra

**Actores:** Carlos (usuario id=7)  
**Flujo:**

1. Carlos confirma compra con boletos 1 y 2 (Dune) → `POST /api/v1/compras`.
2. `CompraService.guardar()` calcula total vía Feign (`BoletoClient`: 45.000 + 45.000), guarda la compra y publica `compras.compra.created`:

   ```json
   { "idCompra": 1, "idUsuario": 7, "total": 90000, "estado": "PENDIENTE" }
   ```

3. **ms-pagos** actualiza `proy_compras`.

**Utilidad:** Pagos puede iniciar el cobro sabiendo el monto y estado **aunque la compra se haya creado hace milisegundos**, sin acoplamiento directo entre bases de datos.

---

### Escenario D — Se registra el pago

**Actores:** Carlos paga con WebPay  
**Flujo:**

1. `ms-pagos` registra o aprueba el pago → `POST /api/v1/pagos` o `PUT /api/v1/pagos/aprobar/id/{id}`.
2. `PagoService` publica `pagos.pago.created` o `pagos.pago.updated`:

   ```json
   { "idPago": 1, "monto": 90000, "metodo": "WEBPAY", "estado": "APROBADO" }
   ```

3. **ms-compras** actualiza `proy_pagos`.

**Utilidad:** Reportes puede generar *"Ventas Mensuales - Mayo 2025"* cruzando proyecciones locales de compras y pagos, sin exportar CSVs manualmente ni hacer joins entre bases de datos.

---

### Escenario E — Cambio de estado de un boleto (reserva o venta)

**Actores:** Sistema de compras reserva un boleto  
**Flujo:**

1. `ms-boletos` actualiza boleto **#3** de `Reservado` → `Vendido` (`PUT /api/v1/boletos/id/3`).
2. `BoletoService.update()` publica `boletos.boleto.updated`.
3. `ms-compras` y `ms-usuarios` actualizan `estado` en `proy_boletos`.

**Utilidad:** Otro cliente que intente comprar el mismo boleto verá en la proyección de compras que ya no está disponible. Este es el Pelicula más crítico según los comentarios en `BoletoUpdatedEvent`:

> *"Viajará constantemente por Kafka cada vez que un boleto pase de Disponible a Reservado o Vendido"*

---

## 6. Patrón de diseño utilizado

### Event-Carried State Transfer (ECST)

Cada Pelicula lleva **los datos mínimos** que el consumidor necesita para actualizar su proyección, no solo un ID:

|         Pelicula       |          Datos incluidos         | Datos omitidos (a propósito) |
| -------------------- | -------------------------------- | ---------------------------- |
| `BoletoCreatedEvent` | id, Pelicula, zona, código, estado | fecha emisión, reservas      |
| `BoletoUpdatedEvent` | id, Pelicula, código, estado       | zona (no cambia normalmente) |
| `CompraCreatedEvent` | id, usuario, total, estado       | detalle de líneas            |
| `PagoCreatedEvent`   | id, monto, método, estado        | transacciones bancarias      |

Esto reduce llamadas Feign de "completar datos" después de recibir un Pelicula.

### Upsert en proyecciones

Los servicios de proyección siguen el patrón:

```java
// Si existe → actualizar; si no → crear
ProyBoleto proy = repository.findById(id)
    .orElse(ProyBoleto.builder().idBoleto(id).build());
proy.setEstado(nuevoEstado);
repository.save(proy);
```

Esto hace los consumidores **idempotentes**: si Kafka reenvía un mensaje, el resultado es el mismo.

---

## 7. Peliculas `updated` (estado actual)

| Pelicula | Producer conectado | Cuándo se emite |
|--------|-------------------|-----------------|
| `compras.compra.updated` | `CompraService.confirmar()` | Al aprobar pago y marcar compra `COMPLETADA` |
| `pagos.pago.updated` | `PagoService.aprobar()` | Al pasar pago a `APROBADO` |
| `catalogo.Pelicula.updated` | `PeliculaService.actualizar()` | Al editar un Pelicula |
| `boletos.boleto.updated` | `BoletoService.update()` | Al cambiar estado o datos del boleto |

Los consumers de `*.updated` ya estaban implementados; los producers de compra y pago se conectaron en el flujo de confirmación/aprobación.

---

## 8. Cómo probar manualmente

### Prerrequisitos

```bat
run-dockers.bat
```

Scripts `init-multi-db/*.sql` (orden 00 → 10). Si cambió el seed, recrear volúmenes con `docker compose down -v`.

### Secuencia sugerida

1. **Crear Pelicula** en catálogo → verificar fila en `boletos.proy_Peliculas`
2. **Crear boleto** → verificar `compras.proy_boletos` y `usuarios.proy_boletos`
3. **Crear compra** → verificar `pagos.proy_compras`
4. **Crear/aprobar pago** → verificar `compras.proy_pagos`

### Verificar Kafka UI

Abrir `http://localhost:8080` (Kafka UI del docker-compose) y confirmar que los topics reciben mensajes al ejecutar cada paso.

### Logs

Con perfil `dev`, buscar en consola:

```
Enviando Pelicula Kafka → topic: boletos.boleto.created, key: 3
Pelicula recibido → boleto created, idBoleto: 3
```

---

## 9. Resumen ejecutivo

|         Pregunta          |                                         Respuesta                                               |
| ------------------------- | ----------------------------------------------------------------------------------------------- |
| **¿Qué se hizo?** | Cadena Kafka entre los 5 MS de negocio: catálogo → boletos → compras/pagos |
| **¿Beneficio?** | Tablas `proy_*` sincronizadas sin acoplar bases de datos |
| **¿Próximo paso?** | Marcar boletos vendidos al confirmar compra; más anotaciones Swagger |

---

## 10. Archivos tocados (referencia rápida)

```
common/
  event/CompraEvent.java
  event/CompraCreatedEvent.java
  event/CompraUpdatedEvent.java
  event/PagoEvent.java
  event/PagoCreatedEvent.java
  event/PagoUpdatedEvent.java

ms-catalogo/
  service/PeliculaService.java
  event/PeliculaEventProducer.java

ms-boletos/
  service/BoletoService.java

ms-compras/
  service/CompraService.java
  event/BoletoEventConsumer.java
  event/PagoEventConsumer.java
  event/CompraEventProducer.java

ms-usuarios/
  event/BoletoEventConsumer.java

ms-pagos/
  service/PagoService.java
  event/CompraEventConsumer.java
  event/PagoEventProducer.java
```

---

*Documento generado como parte de la implementación Kafka en TicketCine.*
