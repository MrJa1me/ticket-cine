package cl.ticketcine.reserva.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class reservaRequest {

    @NotNull(message = "El ID de la sala es obligatorio")
    @Size(min = 1, max = 10, message = "El ID de la sala debe tener entre 1 y 10 caracteres")
    private String idSala;

    @NotNull(message = "El ID del asiento es obligatorio")
    @Min(value = 1, message = "El ID del asiento debe ser mayor que 0")
    private Integer idAsiento;

    @NotNull(message = "La fila es obligatoria")
    @Size(min = 1, max = 1, message = "La fila debe ser un carácter")
    private String fila;

    @NotNull(message = "El número de asiento es obligatorio")
    @Min(value = 1, message = "El número de asiento debe ser mayor que 0")
    private Integer numero;
}
