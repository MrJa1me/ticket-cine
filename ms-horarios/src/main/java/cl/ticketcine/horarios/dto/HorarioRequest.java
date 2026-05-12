package cl.ticketcine.horarios.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HorarioRequest {

    @NotBlank(message = "La película es obligatoria")
    @Size(max = 150, message = "El nombre de la película no puede superar los 150 caracteres")
    private String pelicula;

    @NotBlank(message = "La sala es obligatoria")
    @Size(max = 50, message = "El nombre de la sala no puede superar los 50 caracteres")
    private String sala;

    @NotNull(message = "La fecha del horario es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora del horario es obligatoria")
    private LocalTime hora;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
    private BigDecimal precio;
}
