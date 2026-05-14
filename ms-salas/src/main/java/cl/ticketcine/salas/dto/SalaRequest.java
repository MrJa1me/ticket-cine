package cl.ticketcine.salas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SalaRequest {

    @NotBlank(message = "El identificador de la sala es obligatorio")
    @Size(max = 10, message = "El identificador de la sala no puede superar los 10 caracteres")
    private String idSala;

    @NotBlank(message = "El formato de la sala es obligatorio")
    @Size(max = 10, message = "El formato de sala no puede superar los 10 caracteres")
    private String formato;

    @NotNull(message = "La capacidad de la sala es obligatoria")
    @Min(value = 1, message = "La sala debe tener al menos 1 asiento")
    @Max(value = 999, message = "La sala no puede tener más de 999 asientos")
    private Integer capacidad;
}
