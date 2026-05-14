package cl.ticketcine.validacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class QrCodigoRequest {

    @NotNull(message = "El id de reserva es obligatorio")
    private UUID reservaId;

    @NotBlank(message = "El contenido QR es obligatorio")
    private String hashCode;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}
