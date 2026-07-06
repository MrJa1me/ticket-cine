package cl.ticketcine.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para la solicitud de inicio de sesión (login).
 * Solo requiere correo y contraseña.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener formato válido")
    @Schema(example = "ana@administrador.cl")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(example = "TicketCine@2026")
    private String password;
}
