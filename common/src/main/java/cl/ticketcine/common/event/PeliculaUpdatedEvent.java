package cl.ticketcine.common.event;

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
public class PeliculaUpdatedEvent implements PeliculaEvent {
    private Integer idPelicula;
    private String nombrePelicula;
    private String categoria;
    private LocalDate fecha;
    private String estado;
}
