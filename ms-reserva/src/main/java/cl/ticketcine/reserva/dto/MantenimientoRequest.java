package cl.ticketcine.reserva.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;

@Data
public class MantenimientoRequest {

    @NotNull(message = "El ID de la sala es obligatorio")
    @Size(min = 1, max = 10, message = "El ID de la sala debe tener entre 1 y 10 caracteres")
    private String idSala;

    @NotNull(message = "La fecha de mantenimiento es obligatoria")
    private LocalDate ultimaFecha;

    @NotNull(message = "El nombre del técnico es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre del técnico debe tener entre 1 y 100 caracteres")
    private String tecnicoNombre;
}
