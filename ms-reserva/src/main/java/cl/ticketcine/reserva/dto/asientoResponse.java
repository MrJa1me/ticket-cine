package cl.ticketcine.reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class asientoResponse {

    private Integer idAsiento;
    private String idSala;
    private String fila;
    private Integer numero;
}
