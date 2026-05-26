package cl.ticketcine.reserva.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class salaRequest {

    @NotBlank(message = "El id de la sala es obligatorio")
    @Size(max = 10, message = "El id de la sala no puede superar los 10 caracteres")
    private String idSala;

    @Size(max = 10, message = "El formato no puede superar los 10 caracteres")
    private String formato;

    @Min(value = 1, message = "La capacidad debe ser mayor que 0")
    private Integer capacidad;
}
