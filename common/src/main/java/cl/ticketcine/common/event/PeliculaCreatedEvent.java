package cl.ticketcine.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeliculaCreatedEvent implements PeliculaEvent {

    private String slug;
    private String titulo;
    private Integer idCat;
    private Integer duracionMin;
    private Integer estrenoAnio;

}
