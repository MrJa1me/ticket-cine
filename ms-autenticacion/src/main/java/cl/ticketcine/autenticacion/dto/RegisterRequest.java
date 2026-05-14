package cl.ticketcine.autenticacion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String userEmail;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
    private String password;

    @Pattern(regexp = "^(ROLE_ADMIN|ROLE_CLIENTE|ROLE_OPERADOR)$", message = "El rol debe ser ROLE_ADMIN, ROLE_CLIENTE o ROLE_OPERADOR")
    private String role;
}
