package cl.ticketcine.autenticacion.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthResponse {

    private String token;
    private LocalDateTime expiraAt;
    private String userEmail;
    private String role;
}
