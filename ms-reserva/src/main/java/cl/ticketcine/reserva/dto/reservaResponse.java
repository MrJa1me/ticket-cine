package cl.ticketcine.reserva.dto;

import lombok.Data;

@Data
public class reservaResponse {

    private Integer idAsiento;
    private String idSala;
    private String fila;
    private Integer numero;
}
