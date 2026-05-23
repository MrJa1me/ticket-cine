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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CredencialRepository credencialRepository;
    private final TokenRepository tokenRepository;
    private final BitacoraSesionRepository bitacoraSesionRepository;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        Objects.requireNonNull(request, "La solicitud de registro es obligatoria");
        if (credencialRepository.existsByUserEmail(request.getUserEmail())) {
            throw new IllegalArgumentException("El usuario ya existe: " + request.getUserEmail());
        }
        String roleValue = request.getRole() == null ? Role.ROLE_CLIENTE.name() : request.getRole();
        if (!roleValue.startsWith("ROLE_")) {
            roleValue = "ROLE_" + roleValue;
        }
        Credencial credencial = Credencial.builder()
                .userEmail(request.getUserEmail())
                .passHash(hashPassword(request.getPassword()))
                .mfaHabilitado(false)
                .roles(roleValue)
                .build();
        credencialRepository.save(credencial);

        AuthResponse response = new AuthResponse();
        response.setUserEmail(credencial.getUserEmail());
        response.setRole(roleValue);
        return response;
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Objects.requireNonNull(request, "La solicitud de inicio de sesión es obligatoria");
        Credencial credencial = credencialRepository.findById(request.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!hashPassword(request.getPassword()).equals(credencial.getPassHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String tokenValue = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusHours(4);
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
    }

    public boolean existsByEmail(String email) {
        return credencialRepository.existsByUserEmail(email);
    }

    public UserResponse currentUser(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        Credencial credencial = credencialRepository.findById(emailNotNull)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + emailNotNull));
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

    private String hashPassword(String password) {
        Objects.requireNonNull(password, "La contraseña es obligatoria");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No se pudo generar el hash de la contraseña", e);
        }
    }
}
