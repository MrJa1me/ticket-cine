package cl.ticketcine.reserva.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class asientoRequest {

    @NotBlank(message = "El id de la sala es obligatorio")
    @Size(max = 10, message = "El id de la sala no puede superar los 10 caracteres")
    private String idSala;

    @NotBlank(message = "La fila es obligatoria")
    @Size(min = 1, max = 1, message = "La fila debe tener un único carácter")
    private String fila;

    @Min(value = 1, message = "El número del asiento debe ser mayor que 0")
    private Integer numero;
}
