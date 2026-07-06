package cl.ticketcine.usuarios.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ticketcine.common.security.JwtTokenProvider;
import cl.ticketcine.common.security.TokenBlacklistService;
import cl.ticketcine.usuarios.dto.LoginRequest;
import cl.ticketcine.usuarios.dto.LoginResponse;
import cl.ticketcine.usuarios.dto.RegisterRequest;
import cl.ticketcine.usuarios.dto.UsuarioResponse;
import cl.ticketcine.usuarios.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Login, registro y cierre de sesión (endpoints públicos, sin JWT previo)")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @Operation(
        summary = "Iniciar sesión",
        description = "Valida correo y contraseña. Retorna un token JWT para usar en el botón Authorize de Swagger o en el header Authorization: Bearer <token>"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Credenciales de acceso", required = true,
                content = @Content(schema = @Schema(implementation = LoginRequest.class)))
            @Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea una cuenta con rol Cliente. No requiere autenticación previa."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "409", description = "El correo ya está registrado", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo usuario", required = true,
                content = @Content(schema = @Schema(implementation = RegisterRequest.class)))
            @Valid @RequestBody RegisterRequest request) {
        UsuarioResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Cerrar sesión",
        description = "Invalida el token JWT actual agregándolo a una blacklist en memoria hasta su expiración natural."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sesión cerrada exitosamente", content = @Content),
        @ApiResponse(responseCode = "401", description = "Token ausente o inválido", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @Parameter(description = "Header Authorization con el token JWT", required = true,
                example = "Bearer eyJhbGciOiJIUzI1NiJ9...")
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);
        Date expiration = jwtTokenProvider.getExpirationFromToken(token);
        tokenBlacklistService.addToBlacklist(token, expiration);

        String correo = jwtTokenProvider.getCorreoFromToken(token);
        log.info("Logout exitoso para: {}", correo);

        return ResponseEntity.ok(Map.of("message", "Sesión cerrada exitosamente"));
    }
}
