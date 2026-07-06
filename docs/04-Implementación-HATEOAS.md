# Implementación de HATEOAS en TicketCine

HATEOAS (Hypermedia as the Engine of Application State) añade enlaces `_links` en las respuestas REST para que el cliente descubra acciones disponibles sin memorizar URLs.

---

## Ejemplo

**Sin HATEOAS:**

```json
{
  "idPelicula": 1,
  "nombrePelicula": "Dune: Parte Dos",
  "categoria": "Estreno"
}
```

**Con HATEOAS:**

```json
{
  "idPelicula": 1,
  "nombrePelicula": "Dune: Parte Dos",
  "categoria": "Estreno",
  "_links": {
    "self":   { "href": "http://localhost:9003/api/v1/Peliculas/id/1" },
    "update": { "href": "http://localhost:9003/api/v1/Peliculas/id/1" },
    "delete": { "href": "http://localhost:9003/api/v1/Peliculas/id/1" },
    "all":    { "href": "http://localhost:9003/api/v1/Peliculas" }
  }
}
```

---

## Estado por microservicio

| MS | Controlador | Estado |
|----|-------------|--------|
| ms-catalogo | `PeliculaController` | ✅ Listado, GET, PUT con links |
| ms-boletos | `BoletoController` | ✅ CRUD con links |
| ms-usuarios | `UsuarioController` | ✅ CRUD + activar/desactivar |
| ms-compras | `CompraController`, `CarritoController` | ❌ Pendiente |
| ms-pagos | `PagoController` | ❌ Pendiente |
| ms-usuarios | `AuthController` | ❌ No aplica (transaccional) |
| ms-boletos | `ProyPeliculaController` | ❌ Solo lectura, sin links |

---

## `PeliculaController` (ms-catalogo)

Puerto **9003**, base `/api/v1/Peliculas`.

| Endpoint | Links |
|----------|-------|
| `GET /Peliculas` | Por ítem: self, update, delete, all |
| `GET /Peliculas/id/{id}` | Igual |
| `PUT /Peliculas/id/{id}` | self, delete, all |
| `POST /Peliculas` | Sin HATEOAS en la respuesta |
| `DELETE /Peliculas/id/{id}` | 204 sin cuerpo |
| `GET /Peliculas/existe/idPelicula/{id}` | Boolean utilitario |

**Archivos:** `PeliculaResponseDTO` (extiende `RepresentationModel`), `PeliculaController.addLinks()`.

---

## `BoletoController` (ms-boletos)

Puerto **9002**, base `/api/v1/boletos`.

| Rel | Método | Ruta |
|-----|--------|------|
| `self` | GET | `/api/v1/boletos/id/{id}` |
| `update` | PUT | `/api/v1/boletos/id/{id}` |
| `delete` | DELETE | `/api/v1/boletos/id/{id}` |
| `consultar-precio` | GET | `/api/v1/boletos/precio/id/{id}` |
| `all` | GET | `/api/v1/boletos` |

`GET /precio/id/{id}` devuelve un número (usado por Feign desde compras) — sin HATEOAS.

---

## `UsuarioController` (ms-usuarios)

Puerto **9010**, base `/api/v1/usuarios`.

| Rel | Método | Ruta |
|-----|--------|------|
| `self` | GET | `/api/v1/usuarios/id/{id}` |
| `update` | PUT | `/api/v1/usuarios/id/{id}` |
| `delete` | DELETE | `/api/v1/usuarios/id/{id}` |
| `activar` | PUT | `/api/v1/usuarios/id/{id}/activar` |
| `desactivar` | PUT | `/api/v1/usuarios/id/{id}/desactivar` |
| `all` | GET | `/api/v1/usuarios` |

`POST /perfiles/idUsuario/{id}` devuelve `PerfilResponse` sin hipermedia (mejora opcional).

---

## Dependencia Maven

En cada MS con HATEOAS:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

Patrón en el controlador:

```java
usuario.add(linkTo(methodOn(UsuarioController.class).findById(id)).withSelfRel());
```

---

## Roadmap

Ver [MEJORAS-OPCIONALES.md](./MEJORAS-OPCIONALES.md) para compras, pagos y links condicionados por rol JWT.

---

*TicketCine — HATEOAS con Spring HATEOAS (`RepresentationModel`, `CollectionModel`).*
