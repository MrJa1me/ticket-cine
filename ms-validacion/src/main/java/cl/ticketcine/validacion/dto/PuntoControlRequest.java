package cl.ticketcine.validacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PuntoControlRequest {

    @NotBlank(message = "El id del punto es obligatorio")
    @Size(max = 10, message = "El id del punto no puede superar los 10 caracteres")
    private String idPunto;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(max = 50, message = "La ubicación no puede superar los 50 caracteres")
    private String ubicacion;
}
