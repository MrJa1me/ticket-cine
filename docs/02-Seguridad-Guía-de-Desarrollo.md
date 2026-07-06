# Guía de Desarrollo: Seguridad JWT en TicketCine

Esta guía describe cómo está implementada la autenticación y autorización JWT en **TicketCine** (microservicios de venta de entradas de cine).

## Etapa 1: Comprender la Arquitectura de Seguridad

Antes de escribir código, debemos comprender dos conceptos fundamentales:
1. **Autenticación (¿Quién eres?):** El microservicio `ms-usuarios` verificará las credenciales del usuario y, si son correctas, firmará y devolverá un token JWT.
2. **Autorización (¿Qué puedes hacer?):** Los demás microservicios (`ms-catalogo`, `ms-boletos`, `ms-compras`, `ms-pagos`) validan el JWT y el rol.

**Matriz de permisos (TicketCine):**
- **Cliente:** Lectura de catálogo y boletos; carrito y compras propias.
- **Organizador:** Gestión de películas y boletos; consulta de usuarios, compras y pagos.
- **Administrador:** Control total, incluido CRUD de usuarios.

---

## Etapa 2: Agregar Dependencias Maven

¿Por qué? Necesitamos librerías para manejar criptografía (Spring Security) y generación/validación de tokens (JJWT).

**Paso 2.1: Actualizar el archivo `pom.xml` padre (`C:\TicketCine\pom.xml`)**
Defina las versiones globales. Agregue dentro del bloque `<properties>`:
```xml
<jjwt.version>0.12.6</jjwt.version>
```

Y dentro de `<dependencyManagement><dependencies>`:
```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>${spring-boot.version}</version>
</dependency>
<!-- JJWT API, Impl, Jackson -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>${jjwt.version}</version>
</dependency>
<!-- ... (repetir para jjwt-impl y jjwt-jackson) ... -->
```

**Paso 2.2: Actualizar `common/pom.xml`**
Como todo microservicio depende de `common`, añadir aquí las dependencias garantiza que se propaguen.
Agregue en `<dependencies>`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
</dependency>
<!-- ... (incluir impl y jackson scope runtime) ... -->
```

---

## Etapa 3: Crear la Librería de Seguridad en `common`

Construiremos los cimientos en el módulo compartido para garantizar coherencia en toda la plataforma.

**Paso 3.1: Propiedades JWT**
Cree la clase `JwtProperties.java` en `cl.ticketcine.common.security`. Anotarla con `@ConfigurationProperties(prefix = "jwt")` vincula las claves `secret` y `expiration-ms` del `application.yml` a código Java, evitando strings fijos.

**Paso 3.2: Proveedor de Token**
Cree `JwtTokenProvider.java`. Esta clase utilizará la librería JJWT para firmar tokens usando `SecretKey` y `HMAC-SHA256`. Aquí codificará los métodos de generación (`Jwts.builder()...`) y verificación (`Jwts.parser()...`).

**Paso 3.3: Filtro de Peticiones**
Cree `JwtAuthenticationFilter.java` extendiendo `OncePerRequestFilter`. 
¿Por qué? Este filtro examinará la cabecera `Authorization` de **todas** las peticiones HTTP, validará el "Bearer token" a través de nuestro Provider, e inyectará al usuario en el `SecurityContextHolder` para que Spring lo reconozca como autenticado.

**Paso 3.4: Interceptor Feign**
Cree `FeignClientInterceptor.java` implementando `RequestInterceptor`. Extrae el token actual de Spring Security y lo pega como un nuevo encabezado en peticiones salientes a otros microservicios.

**Paso 3.5: Auto-Configuración**
Modifique `CommonAutoConfiguration.java` para añadir `@EnableConfigurationProperties(JwtProperties.class)`.

**Paso 3.6: Manejador de Excepciones**
En `GlobalExceptionHandler.java`, agregue métodos `@ExceptionHandler` para atrapar `AuthenticationException` (devuelve 401) y `AccessDeniedException` (devuelve 403), logrando respuestas de error limpias en formato JSON.

---

## Etapa 4: Configurar `ms-usuarios` como Servidor de Autenticación

**Paso 4.1: Modelo de Base de datos**
En `Usuario.java`, amplíe la longitud del campo contraseña.
```java
@Column(name = "password", nullable = false, length = 255)
```
¿Por qué? Los hashes generados por algoritmos como BCrypt son largos (usualmente de 60 caracteres) y no caben en una columna de `length=50`.

**Paso 4.2: DTOs para Entrada/Salida**
Cree clases en `cl.ticketcine.usuarios.dto`: `LoginRequest` (`correo`, `password`), `RegisterRequest` y `LoginResponse` (token JWT).

**Paso 4.3: Servicio y Controlador de Autenticación**
Cree `AuthService.java` y `AuthController.java`. Implemente la validación de contraseñas haciendo uso de `passwordEncoder.matches(raw, hashed)`. Si tiene éxito, devuelva un `generarToken()`.

**Paso 4.4: Modificar Servicio de Usuarios**
En `UsuarioService.java`, inyecte `PasswordEncoder`. Durante los procesos de registro (`create` y `update`), asegúrese de codificar la contraseña antes de guardar en repositorio:
```java
usuario.setPassword(passwordEncoder.encode(request.getPassword()));
```

**Paso 4.5: Configuración de Spring Security**
Cree `SecurityConfig.java` en `ms-usuarios`. Deshabilite CSRF, establezca la sesión `STATELESS`, aplique las reglas `.requestMatchers("/api/v1/auth/**").permitAll()`, exija roles para otras rutas, e introduzca el filtro con `.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)`.

**Paso 4.6: Modificar YAML**
En `application.yml`, introduzca el bloque de variables para el JWT (estas coincidirán con `@ConfigurationProperties`):
```yaml
jwt:
  secret: "suClaveBase64..."
  expiration-ms: 3600000
```

---

## Etapa 5: Configurar `ms-catalogo` y demás servidores de recursos

Repita el patrón de `SecurityConfig` en **ms-catalogo**, **ms-boletos**, **ms-compras** y **ms-pagos**:

- Sin rutas `/auth` (solo en ms-usuarios).
- `permitAll` en Swagger y Actuator.
- Reglas por rol según cada dominio (ver [01-Seguridad-JJWT.md](./01-Seguridad-JJWT.md)).
- Mismo bloque `jwt.secret` y `jwt.expiration-ms` en todos los `application.yml`.

---

## Etapa 6: Base de datos inicial (Docker)

**Paso 6.1:** Scripts en `init-multi-db/` (orden `00` → `10`). Contraseñas en BCrypt; clave de prueba: `TicketCine@2026`.

**Paso 6.2:** Recrear volúmenes si cambia el seed:

```bat
run-dockers.bat
```

---

## Etapa 7: Verificación

1. Login fallido sin usuario → `401` (no `500`).
2. **GET** `/api/v1/Peliculas` sin token → `401`.
3. Login Cliente → token → **GET** Peliculas → `200`.
4. Cliente intenta **POST** Pelicula → `403`.
5. Colección Postman: [`TicketCine.postman_collection.json`](./TicketCine.postman_collection.json).
