package cl.ticketcine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CampaniaRequest {

    @NotBlank(message = "El nombre de la campaña es obligatorio")
    @Size(max = 50, message = "El nombre de la campaña no puede superar los 50 caracteres")
    private String nombre;

    @NotNull(message = "La fecha fin es obligatoria")
    private LocalDate fechaFin;
}