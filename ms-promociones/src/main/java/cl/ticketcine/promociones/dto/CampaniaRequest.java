package cl.ticketcine.promociones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaniaRequest {

    @NotBlank(message = "El nombre de la campaña es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha de fin de la campaña es obligatoria")
    private LocalDate fechaFin;
}
