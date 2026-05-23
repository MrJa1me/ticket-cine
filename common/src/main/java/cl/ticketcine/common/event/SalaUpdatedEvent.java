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
public class SalaUpdatedEvent implements SalaEvent {

    private String idSala;
    private String formato;
    private Integer capacidad;

}
