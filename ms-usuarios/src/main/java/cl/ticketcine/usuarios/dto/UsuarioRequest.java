package cl.ticketcine.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato valido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @Size(max = 20, message = "El telefono no puede superar los 20 caracteres")
    private String telefono;

    @Size(max = 2, message = "El codigo de pais debe ser de 2 caracteres")
    private String paisCodigo;
}