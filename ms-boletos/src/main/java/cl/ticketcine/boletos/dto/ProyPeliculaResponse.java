package cl.ticketcine.boletos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProyPeliculaResponse {
    private Integer idPelicula;
    private String nombrePelicula;
    private LocalDate fecha;
}
