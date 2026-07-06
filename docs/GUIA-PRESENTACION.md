# Guía de presentación — TicketCine

Documento para exponer el proyecto en defensa, demo o evaluación. Incluye qué decir, qué mostrar y en qué orden.

**Duración sugerida:** 15–25 minutos (demo completa) · 8–10 minutos (versión corta).

---

## 1. Antes de presentar (checklist)

### 1 día antes

- [ ] Ejecutar `run-dockers.bat` y verificar que todo levanta sin errores.
- [ ] Importar en Postman: `docs/TicketCine.postman_collection.json`.
- [ ] Probar el flujo: Login → Películas → Compra → Pago (carpeta **05 - Flujo de compra**).
- [ ] Tener abiertos en pestañas del navegador: Eureka, Swagger usuarios, Swagger catálogo.

### 30 minutos antes

```bat
run-dockers.bat
```

Esperar a que termine (compila + Docker). Verificar:

```bat
docker compose ps
```

Todos los contenedores deben estar **Up**. Luego comprobar:

| URL | Qué debe verse |
|-----|----------------|
| http://localhost:8761 | Dashboard Eureka con MS registrados |
| http://localhost:9000/actuator/health | `{"status":"UP"}` |
| http://localhost:9010/swagger-ui.html | Swagger ms-usuarios |
| http://localhost:9003/swagger-ui.html | Swagger ms-catalogo |

### Credenciales de demo

| Rol | Correo | Contraseña |
|-----|--------|------------|
| Administrador | `ana@administrador.cl` | `TicketCine@2026` |
| Organizador | `beatriz@organizador.cl` | `TicketCine@2026` |
| Cliente | `carlos@cliente.cl` | `TicketCine@2026` |

### Archivos útiles en `docs/`

| Documento | Para qué |
|-----------|----------|
| Esta guía | Guión de la presentación |
| `05-Api-Gateway-TicketCine.md` | Detalle del gateway |
| `03-Documentación-con-Swagger.md` | Swagger / SpringDoc |
| `04-Implementación-HATEOAS.md` | HATEOAS |
| `01-Seguridad-JJWT.md` | JWT y roles |
| `FLUJO-NEGOCIO-TICKETCINE.md` | Arquitectura y Kafka |
| `TicketCine.postman_collection.json` | Colección Postman |

---

## 2. Mensaje central (30 segundos)

> **TicketCine** es una plataforma de venta de entradas de cine construida con **microservicios** en Java 21 y Spring Boot. Cada servicio tiene su propia base de datos. Se comunican por **REST** (cliente y Feign), **Kafka** (eventos asíncronos) y se descubren con **Eureka**. El cliente entra por un **API Gateway** en el puerto 9000. La seguridad es **JWT** centralizada en `ms-usuarios`.

---

## 3. Arquitectura (2–3 minutos)

### Diagrama para dibujar o mostrar

```
                    ┌─────────────┐
                    │  Postman /  │
                    │  Frontend   │
                    └──────┬──────┘
                           │ :9000
                    ┌──────▼──────┐
                    │ API Gateway │
                    └──────┬──────┘
                           │ Eureka (lb://)
         ┌─────────────────┼─────────────────┐
         ▼                 ▼                 ▼
   ms-usuarios       ms-catalogo        ms-boletos
      :9010              :9003              :9002
         │                 │                 │
         ▼                 ▼                 ▼
   PostgreSQL         PostgreSQL         PostgreSQL
   (usuarios)         (catalogo)         (boletos)

   ms-compras :9004  ←──Feign──→  ms-boletos, ms-pagos
   ms-pagos   :9006  ←──Kafka──→  eventos entre MS
```

### Qué destacar

| Concepto | Frase para la audiencia |
|----------|-------------------------|
| **Microservicios** | 5 servicios de negocio + gateway + Eureka; cada uno despliega y escala por separado. |
| **BD por MS** | No hay joins entre bases; la consistencia se logra con Kafka y proyecciones `proy_*`. |
| **API Gateway** | Un solo punto de entrada; el cliente no conoce los puertos internos. |
| **Eureka** | El gateway resuelve `lb://ms-catalogo` sin hardcodear IPs. |
| **JWT** | Solo `ms-usuarios` emite tokens; el resto valida con la librería `common`. |

---

## 4. Guión de demo (orden recomendado)

### Bloque A — Infraestructura (3 min)

#### A.1 Eureka — Service Discovery

1. Abrir http://localhost:8761
2. Mostrar instancias registradas: `API-GATEWAY`, `MS-USUARIOS`, `MS-CATALOGO`, `MS-BOLETOS`, `MS-COMPRAS`, `MS-PAGOS`.
3. **Decir:** *"Cuando un microservicio arranca, se registra aquí. El gateway usa estos nombres para enrutar."*

#### A.2 API Gateway — Punto de entrada único

1. Abrir http://localhost:9000/actuator/gateway/routes
2. Mostrar rutas: `/api/v1/auth/**` → ms-usuarios, `/api/v1/Peliculas/**` → ms-catalogo, etc.
3. **Decir:** *"El cliente siempre llama al puerto 9000. El path no cambia al llegar al microservicio."*
4. Opcional: http://localhost:9000/actuator/health

**No mostrar Swagger en :9000** — no está en el gateway (WebFlux vs servlet). Swagger va en el puerto directo de cada MS.

---

### Bloque B — Seguridad JWT (3 min)

#### B.1 Swagger en ms-usuarios (login)

1. Abrir http://localhost:9010/swagger-ui.html
2. Expandir **Autenticación** → `POST /api/v1/auth/login`
3. **Try it out** con:

```json
{
  "correo": "ana@administrador.cl",
  "password": "TicketCine@2026"
}
```

4. Ejecutar → mostrar respuesta con `token`, `rol`, `expiresIn`.
5. **Decir:** *"Solo ms-usuarios conoce contraseñas. El token JWT lleva el rol para autorizar en los demás servicios."*

#### B.2 Authorize en Swagger

1. Clic en **Authorize** (candado arriba a la derecha).
2. Pegar **solo el token** (sin `Bearer `).
3. Probar `GET /api/v1/usuarios` en la sección **Usuarios**.
4. **Decir:** *"La configuración `OpenApiConfig` en el módulo common añade el esquema Bearer en todos los Swagger."*

#### B.3 Rol Cliente → 403 (opcional, impacto alto)

1. En Swagger usuarios, login con `carlos@cliente.cl` / `TicketCine@2026`.
2. Authorize con ese token.
3. Intentar `GET /api/v1/usuarios` → **403 Forbidden**.
4. **Decir:** *"El token es válido, pero el rol Cliente no puede listar usuarios. Eso es autorización, no autenticación."*

---

### Bloque C — Swagger + Catálogo (2 min)

1. Abrir http://localhost:9003/swagger-ui.html
2. Authorize con token de Administrador u Organizador.
3. Mostrar sección **Peliculas** con descripciones (`@Operation`, `@ApiResponses`).
4. Ejecutar `GET /api/v1/Peliculas` → listado de películas seed (Dune, Oppenheimer, etc.).
5. **Decir:** *"SpringDoc genera la documentación desde el código. En catálogo y usuarios añadimos anotaciones para enriquecerla."*

| MS | Swagger UI |
|----|------------|
| ms-usuarios | http://localhost:9010/swagger-ui.html |
| ms-catalogo | http://localhost:9003/swagger-ui.html |
| ms-boletos | http://localhost:9002/swagger-ui.html |
| ms-compras | http://localhost:9004/swagger-ui.html |
| ms-pagos | http://localhost:9006/swagger-ui.html |

---

### Bloque D — HATEOAS (2 min)

1. Con token en Swagger catálogo, ejecutar `GET /api/v1/Peliculas/id/1`.
2. En la respuesta JSON, señalar el bloque **`_links`**:

```json
"_links": {
  "self":   { "href": "http://localhost:9003/api/v1/Peliculas/id/1" },
  "update": { "href": "..." },
  "delete": { "href": "..." },
  "all":    { "href": "..." }
}
```

3. **Decir:** *"HATEOAS permite al cliente descubrir qué acciones puede hacer sin memorizar URLs. Está implementado en Películas, Boletos y Usuarios."*

4. Opcional: mismo ejercicio en http://localhost:9002/swagger-ui.html → `GET /api/v1/boletos/id/1` (links `consultar-precio`, `update`, etc.).

---

### Bloque E — Postman + API Gateway (5 min)

> Aquí demuestras que todo funciona **a través del gateway**, como haría un frontend real.

1. Abrir Postman → colección **TicketCine API** (importada desde `docs/TicketCine.postman_collection.json`).
2. Verificar variable `gateway_url` = `http://localhost:9000`.

#### Secuencia en vivo

| Paso | Request en Postman | Qué comentar |
|------|-------------------|--------------|
| 1 | **01 - Autenticación** → Login Cliente | El script guarda `jwt_token` automáticamente |
| 2 | **02 - Películas** → Listar películas | Misma API que Swagger, pero vía `:9000` |
| 3 | **03 - Boletos** → Consultar precio boleto 3 | Feign usa este endpoint internamente en compras |
| 4 | **05 - Flujo de compra** → Obtener carrito | Carrito seed id `4` |
| 5 | **05** → Agregar boleto al carrito | Cliente arma su compra |
| 6 | **05** → Crear compra | Orquesta compras + pago |
| 7 | **05** → Aprobar pago | Cierra el flujo de negocio |
| 8 | **06 - Seguridad** → Películas sin token (401) | Sin JWT no hay acceso |

3. **Decir:** *"Postman llama siempre al gateway. El gateway enruta a Eureka y el microservicio correcto sin que el cliente conozca el puerto 9003 o 9010."*

---

### Bloque F — Kafka (opcional, 2 min)

Si preguntan por eventos asíncronos:

1. Abrir http://localhost:8080 (Kafka UI).
2. Mostrar topics: `catalogo.Pelicula.created`, `boletos.boleto.created`, `compras.compra.created`, `pagos.pago.created`.
3. **Decir:** *"Cuando creo una película en catálogo, Kafka avisa a boletos para actualizar `proy_Peliculas` sin acoplar las bases de datos."*

Detalle en `docs/KAFKA-IMPLEMENTACION.md`.

---

## 5. Versión corta (8–10 min)

Si el tiempo es limitado, solo estos pasos:

1. **Eureka** (:8761) — 1 min  
2. **Gateway routes** (:9000/actuator/gateway/routes) — 1 min  
3. **Swagger login + Authorize** (:9010) — 2 min  
4. **GET Película con `_links`** (HATEOAS en :9003) — 2 min  
5. **Postman:** Login Cliente → Listar películas → Crear compra → Aprobar pago — 4 min  

---

## 6. Preguntas frecuentes en la defensa

| Pregunta | Respuesta breve |
|----------|-----------------|
| ¿Por qué microservicios? | Escalado independiente, equipos por dominio, BD aislada por servicio. |
| ¿Por qué no Swagger en el gateway? | Gateway es WebFlux; SpringDoc servlet no aplica. Cada MS expone su Swagger. |
| ¿Cómo se comparte la seguridad? | Módulo `common`: `JwtTokenProvider`, `JwtAuthenticationFilter`, `OpenApiConfig`. |
| ¿Qué es HATEOAS? | Links hipermedia en la respuesta JSON (`_links`) para navegar la API. |
| ¿Gateway vs llamar al MS directo? | En producción solo gateway; en dev Swagger va directo al puerto del MS. |
| ¿Qué pasa si falla un MS? | Eureka deja de enrutar; gateway puede devolver 503. Kafka permite proyecciones locales. |
| ¿Dónde está el monorepo? | Maven multi-módulo: `common`, 5 MS, `api-gateway`, `eureka`. |

---

## 7. Si algo falla en vivo

| Problema | Solución rápida |
|----------|-----------------|
| Contenedor caído | `docker compose ps` → `docker compose up -d <servicio>` |
| 503 en gateway | Revisar Eureka; esperar 30 s tras arranque |
| 401 en login | Credencial: `TicketCine@2026` (no `Ritmo@2026` ni `TicketCine2026`) |
| 401 con token | Re-login; token expira en 1 h |
| Swagger no carga | Usar puerto del MS (`:9010`), no `:9000` |
| Postman sin token | Ejecutar primero **Login - Administrador** o **Login - Cliente** |
| Eureka vacío | Esperar registro; refrescar dashboard |

**Plan B:** Mostrar capturas o Swagger + Eureka si Docker falla; explicar arquitectura con `docs/FLUJO-NEGOCIO-TICKETCINE.md`.

---

## 8. Cheat sheet de URLs

```
Eureka          http://localhost:8761
API Gateway     http://localhost:9000
Gateway rutas   http://localhost:9000/actuator/gateway/routes
Kafka UI        http://localhost:8080

Swagger usuarios   http://localhost:9010/swagger-ui.html
Swagger catálogo   http://localhost:9003/swagger-ui.html
Swagger boletos    http://localhost:9002/swagger-ui.html
Swagger compras    http://localhost:9004/swagger-ui.html
Swagger pagos      http://localhost:9006/swagger-ui.html

Postman         Importar docs/TicketCine.postman_collection.json
Arranque        run-dockers.bat
```

---

## 9. Cierre de presentación (30 segundos)

> *"TicketCine demuestra un stack completo de microservicios: discovery con Eureka, entrada única con API Gateway, seguridad JWT, documentación interactiva con Swagger, navegabilidad con HATEOAS, pruebas con Postman y coordinación asíncrona con Kafka. Todo desplegable con Docker mediante `run-dockers.bat`."*

---

*TicketCine — Guía de presentación. Actualizar si cambian puertos, credenciales o colección Postman.*
