package cl.ticketcine.reserva.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SalaRequest {

    @NotNull(message = "El ID de la sala es obligatorio")
    @Size(min = 1, max = 10, message = "El ID de la sala debe tener entre 1 y 10 caracteres")
    private String idSala;

    private String formato;
    private Integer capacidad;
}
