package cl.ticketcine.autenticacion.dto;

import lombok.Data;

@Data
public class UserResponse {

    private String userEmail;
    private String role;
}
