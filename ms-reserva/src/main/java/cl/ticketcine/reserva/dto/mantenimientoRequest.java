package cl.ticketcine.reserva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class mantenimientoRequest {

    @NotBlank(message = "El id de la sala es obligatorio")
    @Size(max = 10, message = "El id de la sala no puede superar los 10 caracteres")
    private String idSala;

    @NotNull(message = "La fecha de ultima revisión es obligatoria")
    private LocalDate ultimaFecha;

    @Size(max = 100, message = "El nombre del técnico no puede superar los 100 caracteres")
    private String tecnicoNombre;
}
