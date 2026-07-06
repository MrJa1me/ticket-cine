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
public class PeliculaDeletedEvent implements PeliculaEvent {
    private Integer idPelicula;
}

//Solo necesita el ID para avisar a los demás que eliminen o inactiven todo lo relacionado a este Pelicula.
