package cl.ticketcine.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PerfilRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    private String usuarioEmail;

    @Size(min = 2, max = 10, message = "El idioma debe tener entre 2 y 10 caracteres")
    private String preferenciaIdioma;

    private Boolean esEstudiante;

    @Size(max = 255, message = "La biografía no puede superar los 255 caracteres")
    private String biografia;
}