package cl.ticketcine.reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class salaResponse {

    private String idSala;
    private String formato;
    private Integer capacidad;
}
