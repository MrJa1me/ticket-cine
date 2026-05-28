package cl.ticketcine.reserva.dto;

import lombok.Data;

@Data
public class ReservaResponse {

    private Integer idAsiento;
    private String idSala;
    private String fila;
    private Integer numero;
}
