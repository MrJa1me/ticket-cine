package cl.ticketcine.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ColaEnvioRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    @Size(max = 100, message = "El email del usuario no puede superar los 100 caracteres")
    private String userEmail;

    @NotBlank(message = "El id de la plantilla es obligatorio")
    @Size(max = 20, message = "El id de la plantilla no puede superar los 20 caracteres")
    private String plantillaId;

    @NotBlank(message = "El estado de envio es obligatorio")
    @Size(max = 10, message = "El estado de envio no puede superar los 10 caracteres")
    private String estadoEnvio;
}
