package cl.ticketcine.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlantillaRequest {

    @NotBlank(message = "El id de la plantilla es obligatorio")
    @Size(max = 20, message = "El id de la plantilla no puede superar los 20 caracteres")
    private String idPlantilla;

    @Size(max = 100, message = "El asunto no puede superar los 100 caracteres")
    private String asunto;

    private String contenido;
}
