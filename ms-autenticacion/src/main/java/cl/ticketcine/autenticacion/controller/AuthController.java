package cl.ticketcine.autenticacion.controller;

import cl.ticketcine.autenticacion.dto.AuthResponse;
import cl.ticketcine.autenticacion.dto.LoginRequest;
import cl.ticketcine.autenticacion.dto.RegisterRequest;
import cl.ticketcine.autenticacion.dto.UserResponse;
import cl.ticketcine.autenticacion.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST de autenticacion.
 *
 * Arquitectura limpia de capas tradicional (Controller -> Service -> Repository -> DB).
 * Sin dependencias de Spring Security. La autenticacion se valida directamente
 * contra la base de datos mediante consultas JPA.
 *
 * Endpoints:
 * - POST /api/v1/auth/register -> Registro de nueva credencial
 * - POST /api/v1/auth/login    -> Inicio de sesion
 * - GET  /api/v1/auth/me       -> Usuario actual (requiere email en header)
 * - GET  /api/v1/auth/admin/users -> Listado de usuarios (administrativo)
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> currentUser(@RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(authService.currentUser(email));
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserResponse>> listUsers() {
        return ResponseEntity.ok(authService.findAllUsers());
    }

    @GetMapping("/existe/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authService.existsByEmail(email));
    }
}
