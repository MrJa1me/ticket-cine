# Documentación con Swagger (SpringDoc OpenAPI) — TicketCine

Este documento explica qué es Swagger, cómo funciona en Spring Boot y cómo está implementado en **TicketCine**.

---

## 1. ¿Qué es Swagger?

**Swagger** es un ecosistema de herramientas para diseñar, documentar y consumir APIs REST. Su componente más visible es **Swagger UI**: una interfaz web interactiva que permite explorar y probar los endpoints de una API directamente desde el navegador, sin necesidad de herramientas externas como Postman.

En el mundo Spring Boot moderno, Swagger se integra a través de la librería **SpringDoc OpenAPI**, que genera automáticamente la especificación [OpenAPI 3.0](https://spec.openapis.org/oas/v3.0.3) a partir del código fuente.

### ¿Qué problema resuelve?

Sin Swagger, documentar una API implica mantener un archivo separado (Word, PDF, wiki) que se desincroniza rápidamente del código. Con SpringDoc OpenAPI:

- La documentación **se genera automáticamente** a partir de los controladores y DTOs.
- La documentación **siempre está sincronizada** con el código real.
- Los desarrolladores pueden **probar endpoints** directamente desde el navegador.
- El equipo de frontend puede **descubrir la API** sin leer el código fuente.

### Comparación rápida

| Aspecto | Sin Swagger | Con Swagger |
|---------|-------------|-------------|
| Documentación | Manual (Word, Confluence) | Automática desde el código |
| Sincronización | Se desactualiza rápidamente | Siempre al día |
| Pruebas rápidas | Requiere Postman/cURL | Desde el navegador |
| Descubrimiento | Leer código fuente | Interfaz visual interactiva |
| Autenticación | Copiar headers manualmente | Botón "Authorize" con JWT |

---

## 2. Arquitectura en TicketCine

SpringDoc OpenAPI está integrado en **tres capas**:

```
┌─────────────────────────────────────────────────────────┐
│                     common (librería)                    │
│  OpenApiConfig.java → esquema Bearer JWT (botón 🔒)     │
│  springdoc-openapi-starter-webmvc-ui                     │
└────────────┬──────────┬──────────┬──────────┬───────────┘
             │          │          │          │
     ┌───────▼───┐ ┌────▼────┐ ┌───▼────┐ ┌──▼─────┐ ┌──▼────┐
     │ms-usuarios│ │ms-catálogo│ │ms-boletos│ │compras│ │pagos │
     │   :9010   │ │  :9003  │ │ :9002  │ │ :9004 │ │:9006 │
     │ @Tag full │ │ @Tag full│ │ auto   │ │ auto  │ │ auto │
     └───────────┘ └─────────┘ └────────┘ └───────┘ └──────┘
```

> El **API Gateway** (`:9000`, WebFlux) **no** incluye Swagger. Usa el puerto directo de cada MS.

### URLs de acceso (Docker / `run-dockers.bat`)

| Microservicio | Puerto | Swagger UI | API principal |
|---------------|--------|------------|---------------|
| ms-usuarios | 9010 | http://localhost:9010/swagger-ui.html | `/api/v1/auth/login` |
| ms-catalogo | 9003 | http://localhost:9003/swagger-ui.html | `/api/v1/Peliculas` |
| ms-boletos | 9002 | http://localhost:9002/swagger-ui.html | `/api/v1/boletos` |
| ms-compras | 9004 | http://localhost:9004/swagger-ui.html | `/api/v1/compras` |
| ms-pagos | 9006 | http://localhost:9006/swagger-ui.html | `/api/v1/pagos` |

OpenAPI JSON: `http://localhost:<puerto>/v3/api-docs`

**Credenciales de prueba:** `ana@administrador.cl` / `TicketCine@2026` → copiar `token` → botón **Authorize** en Swagger (solo el token, sin `Bearer `).

---

## 3. Dependencias Maven

La versión de SpringDoc se centraliza en el **POM padre** dentro de `<dependencyManagement>`, y cada módulo hijo la hereda sin especificar versión.

### 3.1. POM padre (`pom.xml` raíz)

```xml
<properties>
    <springdoc-openapi.version>2.8.17</springdoc-openapi.version>
</properties>

<dependencyManagement>
    <dependencies>
        <!-- [SWAGGER-INI] -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc-openapi.version}</version>
        </dependency>
        <!-- [SWAGGER-FIN] -->
    </dependencies>
</dependencyManagement>
```

### 3.2. Módulos hijos (common + cada ms-*)

Cada microservicio de negocio declara la dependencia **sin versión** (la hereda del padre):

```xml
<!-- [SWAGGER-INI] -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
<!-- [SWAGGER-FIN] -->
```

### ¿Qué trae esta dependencia?

| Componente | Descripción |
|------------|-------------|
| `springdoc-openapi-starter-webmvc-api` | Escanea controladores y genera la especificación OpenAPI 3.0 |
| `swagger-ui` | Interfaz web interactiva (HTML/JS/CSS) empaquetada como WebJar |
| `swagger-annotations` | Anotaciones Java: `@Operation`, `@Tag`, `@Schema`, etc. |

---

## 4. Configuración

### 4.1. `application.yml` (en cada microservicio)

```yaml
# [SWAGGER-INI]
springdoc:
  api-docs:
    path: /v3/api-docs        # URL donde se sirve el JSON de OpenAPI
  swagger-ui:
    path: /swagger-ui.html    # URL de entrada a la interfaz Swagger UI
# [SWAGGER-FIN]
```

> Estos valores son los defaults de SpringDoc, pero se declaran explícitamente para que quede claro cuáles son las rutas y facilitar su búsqueda en el código.

### 4.2. Spring Security — Rutas públicas de Swagger

En cada `SecurityConfig.java` se permiten las rutas de Swagger sin autenticación:

```java
// [SWAGGER-INI]
// PERMITIR RUTAS PÚBLICAS DE SWAGGER / SPRINGDOC
.requestMatchers(
    "/v3/api-docs/**",       // Especificación OpenAPI (JSON)
    "/v3/api-docs.yaml",     // Especificación OpenAPI (YAML)
    "/swagger-ui/**",        // Archivos estáticos de la interfaz
    "/swagger-ui.html",      // Punto de entrada de Swagger UI
    "/swagger-resources/**", // Compatibilidad con versiones anteriores
    "/webjars/**",           // Recursos estáticos empaquetados
    "/favicon.ico"           // Ícono del navegador
).permitAll()
// [SWAGGER-FIN]
```

Sin estas reglas, Spring Security bloquearía el acceso a Swagger UI con un `401 Unauthorized`.

### 4.3. Configuración global de seguridad JWT — `OpenApiConfig.java` (common)

Esta clase vive en el módulo `common` y se auto-configura en todos los microservicios. Define el botón 🔒 **Authorize** en Swagger UI para enviar tokens JWT:

```java
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Aplica el esquema de seguridad a TODOS los endpoints
                .addSecurityItem(
                    new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                // Define el esquema: Bearer Token con formato JWT
                .components(new Components()
                    .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Introduce únicamente tu token JWT. "
                            + "La librería añade 'Bearer' automáticamente.")
                    )
                );
    }
}
```

**¿Qué hace cada parte?**

| Elemento | Propósito |
|----------|-----------|
| `SecurityScheme.Type.HTTP` | Indica que es autenticación HTTP estándar |
| `.scheme("bearer")` | Especifica que es un Bearer Token |
| `.bearerFormat("JWT")` | Indica que el formato del token es JWT (informativo) |
| `addSecurityItem(...)` | Aplica el esquema globalmente a todos los endpoints |

**Resultado en Swagger UI:**
Al hacer clic en el botón 🔒 **Authorize**, aparece un cuadro de texto donde se pega el token JWT. A partir de ahí, todas las peticiones incluyen automáticamente el header `Authorization: Bearer <token>`.

---

## 5. Anotaciones de Swagger en los controladores

SpringDoc genera documentación automáticamente a partir de los controladores Spring MVC, pero las **anotaciones de Swagger** permiten enriquecer esa documentación con descripciones, ejemplos y esquemas explícitos.

### 5.1. Catálogo de anotaciones disponibles

| Anotación | Se aplica en | Propósito |
|-----------|--------------|-----------|
| `@Tag` | Clase (Controller) | Agrupa endpoints bajo un nombre descriptivo en Swagger UI |
| `@Operation` | Método (endpoint) | Describe qué hace el endpoint (`summary` y `description`) |
| `@ApiResponses` | Método | Envuelve múltiples `@ApiResponse` |
| `@ApiResponse` | Método | Documenta un código de respuesta HTTP (200, 201, 404, etc.) |
| `@Parameter` | Parámetro | Describe un parámetro de ruta/query (`description`, `example`) |
| `@RequestBody` | Parámetro | Describe el cuerpo de la petición (versión de Swagger) |
| `@Schema` | DTO / Campo | Define el tipo y estructura del modelo de datos |
| `@Content` | Dentro de `@ApiResponse` | Especifica el tipo de contenido y su esquema |
| `@ArraySchema` | Dentro de `@Content` | Para respuestas que devuelven listas/arrays |

> **Importante:** Todas las anotaciones vienen del paquete `io.swagger.v3.oas.annotations.*`.

### 5.2. Ejemplo — `PeliculaController` y `AuthController`

Controladores de referencia con `@Tag`, `@Operation`, `@ApiResponses`:

- `ms-catalogo` → `PeliculaController` (`/api/v1/Peliculas`)
- `ms-usuarios` → `AuthController`, `UsuarioController` (`/api/v1/auth`, `/api/v1/usuarios`)

Los demás MS generan documentación automática desde los mappings; se pueden añadir anotaciones siguiendo el mismo patrón.

En Swagger UI, los endpoints aparecen agrupados bajo **Peliculas** o **Autenticación** según el `@Tag` del controlador.

#### `@Operation` + `@ApiResponses` — GET lista

```java
@Operation(summary = "Obtener todos los Películas", description = "Retorna la lista completa del catálogo")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = PeliculaResponseDTO.class))))
})
@GetMapping
public ResponseEntity<CollectionModel<PeliculaResponseDTO>> listar() { ... }
```

#### `@Parameter` — Path variable

```java
@GetMapping("/id/{id}")
public ResponseEntity<PeliculaResponseDTO> obtener(
    @Parameter(description = "ID de la película", required = true, example = "1")
    @PathVariable Integer id) { ... }
```

#### `@RequestBody` de Swagger

```java
@PostMapping
public ResponseEntity<PeliculaResponseDTO> crear(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos de la película", required = true,
        content = @Content(schema = @Schema(implementation = PeliculaRequestDTO.class)))
    @Valid @RequestBody PeliculaRequestDTO request) { ... }
```

> Usar el FQN `@io.swagger.v3.oas.annotations.parameters.RequestBody` para no confundirlo con el `@RequestBody` de Spring MVC.

### 5.3. Imports necesarios

Para anotar un controlador completo se necesitan estos imports:

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
```

---

## 6. Documentación automática vs. explícita

SpringDoc genera documentación incluso **sin anotaciones**. La diferencia radica en la calidad:

### Sin anotaciones (auto-generado)

SpringDoc detecta automáticamente:
- Las rutas (`@GetMapping`, `@PostMapping`, etc.)
- Los tipos de parámetros (`@PathVariable`, `@RequestParam`)
- Los tipos de retorno (`ResponseEntity<T>`)
- Las validaciones de Jakarta Validation (`@NotBlank`, `@Size`, `@Pattern`)

```json
{
  "summary": "findAll",
  "operationId": "findAll",
  "responses": {
    "200": { "description": "OK" }
  }
}
```

### Con anotaciones (documentación explícita)

```json
{
  "summary": "Obtener todos los Películas",
  "description": "Retorna la lista completa del catálogo",
  "operationId": "findAll",
  "responses": {
    "200": {
      "description": "Lista obtenida exitosamente",
      "content": {
        "application/json": {
          "schema": {
            "type": "array",
            "items": { "$ref": "#/components/schemas/PeliculaResponseDTO" }
          }
        }
      }
    }
  }
}
```

> **Recomendación:** Documentar con anotaciones los endpoints de auth y catálogo (`PeliculaController`, `AuthController`). El resto puede usar auto-generación hasta replicar el patrón.

---

## 7. Validaciones de Jakarta y Swagger

SpringDoc lee las anotaciones Jakarta Validation de los DTOs y las refleja en OpenAPI (`PeliculaRequestDTO`, `LoginRequest`, etc.):

```java
@Data
public class PeliculaRequestDTO {

    @NotBlank(message = "El ISBN es obligatorio")
    @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?...$",
             message = "El ISBN debe tener formato ISBN-13 válido")
    private String isbn;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 1, max = 255, message = "El título debe tener entre 1 y 255 caracteres")
    private String titulo;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1450)
    @Max(value = 2100)
    private Integer anioPublicacion;
}
```

Swagger UI mostrará automáticamente:
- Campos marcados como **required** (por `@NotBlank` y `@NotNull`)
- Restricciones de `minLength`, `maxLength` (por `@Size`)
- Restricciones de `minimum`, `maximum` (por `@Min`, `@Max`)
- El `pattern` de la expresión regular (por `@Pattern`)

---

## 8. Flujo de trabajo con Swagger UI

### Paso 1 — Acceder a Swagger UI

Abrir en el navegador la URL del microservicio deseado:

```
http://localhost:9002/swagger-ui.html
```

### Paso 2 — Autenticarse (si los endpoints requieren JWT)

1. Hacer clic en el botón 🔒 **Authorize** (parte superior derecha).
2. Pegar el token JWT obtenido del endpoint `/api/v1/auth/login` de ms-usuarios.
3. Hacer clic en **Authorize** y luego en **Close**.

A partir de aquí, todas las peticiones incluirán el header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

### Paso 3 — Probar un endpoint

1. Abrir Swagger UI del MS, p. ej. http://localhost:9010/swagger-ui.html
2. **Autenticación** → `POST /api/v1/auth/login` con `ana@administrador.cl` / `TicketCine@2026`
3. Copiar `token` → **Authorize** (solo el token)
4. Probar `GET /api/v1/Peliculas` o `GET /api/v1/usuarios`
2. Hacer clic en **Try it out**.
3. Completar los parámetros requeridos (ej. `id = 1`).
4. Hacer clic en **Execute**.
5. Revisar la respuesta: código HTTP, headers y body JSON.

---

## 9. Resumen de archivos involucrados

| Archivo | Módulo | Propósito |
|---------|--------|-----------|
| `pom.xml` (raíz) | padre | Centraliza la versión de `springdoc-openapi` (`2.8.17`) |
| `pom.xml` (cada módulo) | todos | Declara la dependencia `springdoc-openapi-starter-webmvc-ui` |
| `OpenApiConfig.java` | common | Configura el esquema Bearer JWT global para Swagger UI |
| `application.yml` | cada MS | Define las rutas de `api-docs` y `swagger-ui` |
| `SecurityConfig.java` | cada MS | Permite acceso público a las rutas de Swagger |
| Controladores con `@Tag`, `@Operation`, etc. | cada MS | Enriquecen la documentación auto-generada |

---

## 10. Referencia rápida de anotaciones

```java
// ── A nivel de CLASE ──────────────────────────────────────────
@Tag(name = "Peliculas", description = "API del catálogo de películas")

// ── A nivel de MÉTODO ─────────────────────────────────────────
@Operation(summary = "Buscar por ID", description = "Descripción larga...")

@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Encontrado",
        content = @Content(schema = @Schema(implementation = MiDTO.class))),
    @ApiResponse(responseCode = "404", description = "No encontrado",
        content = @Content)
})

// ── A nivel de PARÁMETRO ──────────────────────────────────────
@Parameter(description = "ID del recurso", required = true, example = "42")

// ── Para LISTAS ───────────────────────────────────────────────
content = @Content(
    array = @ArraySchema(schema = @Schema(implementation = MiDTO.class)))

// ── Para REQUEST BODY (usar FQN para no confundir con Spring) ─
@io.swagger.v3.oas.annotations.parameters.RequestBody(
    description = "Datos a crear", required = true,
    content = @Content(schema = @Schema(implementation = MiRequest.class)))
```
