package cl.ticketcine.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PerfilRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    private String usuarioEmail;

    @Size(max = 10, message = "La preferencia de idioma no puede superar los 10 caracteres")
    private String preferenciaIdioma;

    private Boolean esEstudiante;

    @Size(max = 255, message = "La biografia no puede superar los 255 caracteres")
    private String biografia;
}