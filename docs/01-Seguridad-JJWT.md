# Implementación de Seguridad con JWT en TicketCine

Este documento detalla la implementación de seguridad basada en JSON Web Tokens (JWT) en el proyecto **TicketCine** (venta de entradas de cine, microservicios Spring Boot).

## 1. Introducción a JWT

Un JSON Web Token (JWT) es un estándar abierto (RFC 7519) que define una forma compacta y autónoma de transmitir información de forma segura entre las partes como un objeto JSON. Esta información puede ser verificada y en la que se puede confiar porque está firmada digitalmente.

Un JWT consta de tres partes separadas por puntos (`.`):

1. **Header (Cabecera):** Típicamente consta de dos partes: el tipo de token (JWT) y el algoritmo de firma utilizado, como HMAC SHA256 o RSA. Por ejemplo:

```json
{
   "alg": "HS256",
   "typ": "JWT"
}
```

2. **Payload (Carga útil):** Contiene los *claims* (declaraciones). Los claims son declaraciones sobre una entidad (típicamente, el usuario) y datos adicionales. Hay claims registrados (estándar), públicos y privados. En nuestro caso, incluimos el email, rol y nombre del usuario. Por ejemplo:
```json
{
  "correo": "ana@administrador.cl",
  "rol": "Administrador",
  "nombre": "Ana Aguilar"
}
```

3. **Signature (Firma):** Para crear la parte de la firma, se debe tomar la cabecera codificada, la carga útil codificada, un secreto y el algoritmo especificado en la cabecera, para luego firmarlo. Esto verifica que el remitente del JWT es quien dice ser y asegura que el mensaje no ha sido alterado. Por ejemplo:

```
S3g2YTlCclN0cjhXNXZlekFidTFjRHRmR2g0aktsTXA1TjZPM1F5UjBTOD0
```

Como texto plano, un token real se ve así de largo y continuo (sin espacios ni saltos de línea):
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImNnb21lenZlZ2FAdHJpc2tlbGVkdS5jbCIsInJvbCI6IkFVTUFfUk9MRSIsIm5vbWJyZSI6IkNyaXN0aWFuIEdvbWV6In0.S3g2YTlCclN0cjhXNXZlekFidTFjRHRmR2g0aktsTXA1TjZPM1F5UjBTOD0
```

## 2. Arquitectura de Seguridad del Proyecto

El proyecto implementa un modelo de seguridad distribuido adaptado a microservicios:

* **Microservicio Usuarios (`ms-usuarios`, puerto 9010):** **Servidor de Autenticación**. Verifica credenciales (BCrypt) y emite tokens JWT.
* **Otros microservicios (`ms-catalogo`, `ms-boletos`, `ms-compras`, `ms-pagos`):** **Servidores de Recursos**. Validan el JWT y autorizan por rol.
* **API Gateway (`:9000`):** Enruta peticiones; no emite tokens.
* **Módulo `common`:** Filtros JWT, `JwtTokenProvider`, `OpenApiConfig`, excepciones compartidas.

## 3. Fragmentos de Código Clave

A continuación, se explican los componentes centrales de la implementación:

### A. Proveedor de Tokens (`JwtTokenProvider.java`)

Genera y valida los tokens JWT. Reside en el módulo `common`.

```java
public String generarToken(String email, String rol, String nombreCompleto) {
    Date ahora = new Date();
    Date expiracion = new Date(ahora.getTime() + jwtProperties.getExpirationMs());

    return Jwts.builder()
            .subject(email)                        // Subject = email del usuario
            .claim("rol", rol)                     // Claim personalizado: rol
            .claim("nombre", nombreCompleto)       // Claim personalizado: nombre
            .issuedAt(ahora)                       // Fecha de emisión
            .expiration(expiracion)                // Fecha de expiración
            .signWith(getSigningKey())             // Firma con HMAC-SHA256
            .compact();
}
```

*Explicación:* Crea el token estableciendo el *subject* (identificador principal), *claims* personalizados (rol, nombre), tiempo de validez y firma criptográfica.

### B. Filtro de Autenticación (`JwtAuthenticationFilter.java`)

Intercepta todas las peticiones HTTP para extraer y validar el JWT. Reside en `common`.

```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
    String token = extraerToken(request); // Extrae de "Authorization: Bearer <token>"
    if (StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
        String email = jwtTokenProvider.getEmailFromToken(token);
        String rol = jwtTokenProvider.getRolFromToken(token);
    
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));
        UsernamePasswordAuthenticationToken authentication 
                = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
}
```

*Explicación:* Busca el token en la cabecera `Authorization`. Si existe y es válido, inyecta la identidad del usuario y su rol en el contexto de Spring Security. Si el token es inválido, simplemente deja que Spring Security rechace el acceso.

### C. Configuración de Seguridad (`SecurityConfig.java` - Ejemplo de ms-catalogo)

Define qué endpoints requieren qué permisos. Cada microservicio tiene la suya.

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // Stateless, sin cookies, no hay riesgo CSRF
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/Peliculas/**").hasAnyRole("Administrador", "Organizador", "Cliente")
            .requestMatchers(HttpMethod.POST, "/api/v1/Peliculas/**").hasAnyRole("Administrador", "Organizador")
            // ... otras reglas ...
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
}
```

*Explicación:* Configura la aplicación como "stateless" (sin mantener sesión en memoria), define reglas de autorización basadas en roles (RBAC) y posiciona el filtro JWT antes de los procesos de autenticación predeterminados.

### D. Flujo de Autenticación (`AuthService.java` en ms-usuarios)

Gestión de inicio de sesión con encriptación BCrypt y manejo de bloqueos.

```java
public LoginResponse login(LoginRequest request) {
    Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
        .orElseThrow(() -> new InvalidCredentialsException("Credenciales inválidas"));
  
    // Validar contraseña con BCrypt
    if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
        registrarIntentoFallido(usuario, credencial); // Incrementa intentos y puede bloquear
        throw new RuntimeException("Credenciales inválidas");
    }
  
    registrarAccesoExitoso(usuario, credencial);
    String token = jwtTokenProvider.generarToken(usuario.getCorreo(), usuario.getRol(), ...);
  
    return LoginResponse.builder().token(token).build();
}
```

*Explicación:* Busca al usuario, compara la contraseña en texto plano con el hash almacenado de manera segura usando `PasswordEncoder.matches`, gestiona intentos fallidos para prevenir ataques de fuerza bruta y devuelve el token tras el éxito.

### E. Endpoints Públicos (`AuthController.java` en ms-usuarios)

Expone las interfaces de inicio de sesión y registro público.

```java
@PostMapping("/login")
public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
}

@PostMapping("/register") // Asigna automáticamente rol Cliente y encripta password
public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
}
```

### F. Interceptor Feign (`FeignClientInterceptor.java` en common)

Propaga la identidad en llamadas entre microservicios.

```java
@Override
public void apply(RequestTemplate template) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getCredentials() instanceof String token) {
        template.header("Authorization", "Bearer " + token);
    }
}
```

*Explicación:* Cuando un microservicio (p. ej. `ms-compras`) llama a otro (`ms-boletos`) vía Feign, el interceptor propaga el token del usuario autenticado.

## 4. Configuración (application.yml)

Para que el ecosistema confíe en los mismos tokens, todos los microservicios deben compartir la misma configuración base:

```yaml
jwt:
  secret: "dG9rZW5TZWNyZXRLZXlUaWNrZXRDaW5lMjAyNlNlY3VyaXR5="
  expiration-ms: 3600000   # 1 hora en milisegundos
```

* `secret`: Es una clave generada en Base64. Debe ser larga (mínimo 256 bits para HMAC SHA-256) y guardarse en un lugar seguro (por ejemplo, inyectada mediante variables de entorno). **El valor de este proyecto es solo para desarrollo**.
* `expiration-ms`: Define el tiempo de vida útil de un token desde su creación (aquí configurado a 1 hora).

## 5. Matriz de Autorización por Endpoint

| Microservicio   | Endpoint                         | Método HTTP      | Roles Permitidos                      |
| :-------------- | :------------------------------- | :---------------- | :------------------------------------ |
| `ms-usuarios` | `/api/v1/auth/**` | ALL | Público |
| `ms-usuarios` | `/api/v1/usuarios/**` | GET | Administrador, Organizador |
| `ms-usuarios` | `/api/v1/usuarios/**` | POST, PUT, DELETE | Administrador |
| `ms-catalogo` | `/api/v1/Peliculas/**` | GET | Administrador, Organizador, Cliente |
| `ms-catalogo` | `/api/v1/Peliculas/**` | POST, PUT, DELETE | Administrador, Organizador |
| `ms-boletos` | `/api/v1/boletos/**`, `/proyPeliculas/**` | GET | Administrador, Organizador, Cliente |
| `ms-boletos` | `/api/v1/boletos/**` | POST, PUT, DELETE | Administrador, Organizador |
| `ms-compras` | `/api/v1/carritos/**` | ALL | Cliente, Organizador, Administrador |
| `ms-compras` | `/api/v1/compras/**` | POST, PUT | Cliente, Organizador, Administrador |
| `ms-compras` | `/api/v1/compras` | GET | Organizador, Administrador |
| `ms-pagos` | `/api/v1/pagos/**` | GET (lista) | Organizador, Administrador |
| `ms-pagos` | `/api/v1/pagos/**` | POST | Cliente, Organizador, Administrador |
| `ms-pagos` | `/api/v1/pagos/**` | PUT | Organizador, Administrador |
| `ms-pagos` | `/api/v1/pagos/**` | DELETE | Administrador |
| Todos | `/actuator/**`, Swagger | ALL | Público (desarrollo) |

## 6. Cómo probar con Postman

Siga esta guía paso a paso para testear la seguridad de la API:

### Usuarios de prueba (seed `init-multi-db/10-usuarios.sql`):

* **Administrador:** `ana@administrador.cl` o `admin@ticketcine.cl` / `TicketCine@2026`
* **Organizador:** `beatriz@organizador.cl` / `TicketCine@2026`
* **Cliente:** `carlos@cliente.cl` / `TicketCine@2026`

### Paso 1: Intentar acceder sin credenciales (401)

1. **GET** `http://localhost:9000/api/v1/usuarios` (vía gateway) o `http://localhost:9010/api/v1/usuarios` (directo).
2. Sin header `Authorization` → `401 Unauthorized`.

### Paso 2: Login y obtener token

1. **POST** `http://localhost:9000/api/v1/auth/login`
2. Body JSON:

```json
{
  "correo": "carlos@cliente.cl",
  "password": "TicketCine@2026"
}
```

3. Copiar el `token` de la respuesta.

### Paso 3: Petición autenticada

1. **GET** `http://localhost:9000/api/v1/Peliculas`
2. Authorization → Bearer Token → pegar el token.
3. Respuesta `200` con listado de películas.

### Paso 4: Validar autorización (403)

1. Con token de **Cliente**, intentar **POST** `http://localhost:9000/api/v1/Peliculas` → `403 Forbidden`.
2. Con token de **Organizador** o **Administrador**, la misma operación puede devolver `201`.

## 7. Buenas Prácticas de Seguridad Implementadas (y Recomendadas)

1. **Encriptación Fuerte de Contraseñas:** Se usa **BCrypt** (con salt). Nunca almacenar en texto plano, md5 o sha1.
2. **No Exponer el Hash:** Las contraseñas encriptadas jamás deben ser expuestas en las respuestas de la API (`UsuarioResponse` no la incluye).
3. **Protección Contra Fuerza Bruta:** Se implementó un límite de intentos fallidos (5) que bloquea temporalmente o permanentemente la cuenta en la tabla `credenciales_usuarios`.
4. **JWT Cortos:** Los tokens tienen un tiempo de expiración corto (ej. 1 hora) limitando la ventana de daño si son comprometidos.
5. **Secreto JWT Seguro y Externo:** El secreto (`jwt.secret`) debe ser largo, complejo y nunca exponerse en repositorios públicos. Se debe inyectar vía variables de entorno en producción.
6. **APIs Stateless:** Evita completamente el uso de sesiones de memoria, eliminando vulnerabilidades asociadas y permitiendo una fácil escalabilidad horizontal.
7. **Roles y Privilegios Mínimos (RBAC):** Se aplica el principio de menor privilegio al nivel más granular necesario usando `@EnableWebSecurity` y configuraciones exactas en cada microservicio.
8. **Inmunidad a CSRF:** Dado que es una API Stateless basada en headers en lugar de cookies de sesión automáticas, está inherentemente protegida contra Cross-Site Request Forgery (ataque que suplanta la identidad).
9. **Intercepción Feign Segura:** Las comunicaciones intra-cluster (microservicio a microservicio) propagan contextos de identidad autenticados, evitando abrir endpoints que puedan ser explotados internamente.
10. **Manejo Uniforme de Excepciones:** Se gestionan errores 401 y 403 en `GlobalExceptionHandler` evitando fugas de información interna como stacktraces.
11. **Validación de Entradas:** Todas las clases DTO (`LoginRequest`, `RegisterRequest`) validan firmemente la estructura de los datos que ingresan (largo, formato de email) para prevenir inyecciones.
12. **Uso de HTTPS en Producción (TLS/SSL):** Todo el tráfico debe ser cifrado para evitar interceptación de tokens en texto claro. (Recomendación a implementar en Gateway/Ingress).
13. **Evitar Almacenar Tokens en `localStorage`:** Los clientes web (frontend) no deben usar localstorage por riesgo de ataques XSS; es mejor usar cookies "HttpOnly".
14. **Auditoría de Logs:** Los inicios de sesión incorrectos, así como las cuentas inactivas y bloqueadas, emiten advertencias en los registros de la aplicación (`log.warn`) listos para ser monitoreados con herramientas de observabilidad.
15. **Rotación de Claves:** Se deben proporcionar mecanismos para renovar los tokens secret base (`jwt.secret`) de manera regular y planificada.
