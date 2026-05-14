package cl.ticketcine.autenticacion.service;

import cl.ticketcine.autenticacion.dto.AuthResponse;
import cl.ticketcine.autenticacion.dto.LoginRequest;
import cl.ticketcine.autenticacion.dto.RegisterRequest;
import cl.ticketcine.autenticacion.dto.Role;
import cl.ticketcine.autenticacion.dto.UserResponse;
import cl.ticketcine.autenticacion.model.entity.BitacoraSesion;
import cl.ticketcine.autenticacion.model.entity.Credencial;
import cl.ticketcine.autenticacion.model.entity.Token;
import cl.ticketcine.autenticacion.repository.BitacoraSesionRepository;
import cl.ticketcine.autenticacion.repository.CredencialRepository;
import cl.ticketcine.autenticacion.repository.TokenRepository;
import cl.ticketcine.autenticacion.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CredencialRepository credencialRepository;
    private final TokenRepository tokenRepository;
    private final BitacoraSesionRepository bitacoraSesionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (credencialRepository.existsByUserEmail(request.getUserEmail())) {
            throw new IllegalArgumentException("El usuario ya existe: " + request.getUserEmail());
        }
        String roleValue = request.getRole() == null ? Role.ROLE_CLIENTE.name() : request.getRole();
        if (!roleValue.startsWith("ROLE_")) {
            roleValue = "ROLE_" + roleValue;
        }
        Credencial credencial = Credencial.builder()
                .userEmail(request.getUserEmail())
                .passHash(passwordEncoder.encode(request.getPassword()))
                .mfaHabilitado(false)
                .roles(roleValue)
                .build();
        credencialRepository.save(credencial);

        AuthResponse response = new AuthResponse();
        response.setUserEmail(credencial.getUserEmail());
        response.setRole(roleValue);
        return response;
    }

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getPassword())
            );
            Credencial credencial = credencialRepository.findById(request.getUserEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            var userDetails = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
            String tokenValue = jwtService.generateToken(userDetails);
            LocalDateTime expiration = LocalDateTime.ofInstant(
                    jwtService.extractExpiration(tokenValue).toInstant(),
                    java.time.ZoneId.systemDefault());
            Token token = Token.builder()
                    .credencial(credencial)
                    .jwtSecret(tokenValue)
                    .expiraAt(expiration)
                    .build();
            tokenRepository.save(token);

            if (request.getDispositivo() != null && !request.getDispositivo().isBlank()) {
                BitacoraSesion sesion = BitacoraSesion.builder()
                        .token(token)
                        .dispositivo(request.getDispositivo())
                        .build();
                bitacoraSesionRepository.save(sesion);
            }

            AuthResponse response = new AuthResponse();
            response.setToken(tokenValue);
            response.setExpiraAt(expiration);
            response.setUserEmail(credencial.getUserEmail());
            response.setRole(credencial.getRoleList().isEmpty() ? Role.ROLE_CLIENTE.name() : credencial.getRoleList().get(0));
            return response;
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
    }

    public UserResponse currentUser(String email) {
        Credencial credencial = credencialRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));
        UserResponse response = new UserResponse();
        response.setUserEmail(credencial.getUserEmail());
        response.setRole(credencial.getRoleList().isEmpty() ? Role.ROLE_CLIENTE.name() : credencial.getRoleList().get(0));
        return response;
    }

    public List<UserResponse> findAllUsers() {
        return credencialRepository.findAll().stream().map(credencial -> {
            UserResponse response = new UserResponse();
            response.setUserEmail(credencial.getUserEmail());
            response.setRole(credencial.getRoleList().isEmpty() ? Role.ROLE_CLIENTE.name() : credencial.getRoleList().get(0));
            return response;
        }).toList();
    }
}
