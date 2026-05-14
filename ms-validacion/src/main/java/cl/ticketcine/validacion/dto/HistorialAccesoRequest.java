package cl.ticketcine.validacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HistorialAccesoRequest {

    @NotNull(message = "El id del QR es obligatorio")
    private Long idQr;

    @NotBlank(message = "El id del punto es obligatorio")
    private String idPunto;
}
