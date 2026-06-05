package cl.ticketcine.promociones.dto;

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
public class CampaniaResponse {

    private Long idCamp;
    private String nombre;
    private LocalDate fechaFin;
}
