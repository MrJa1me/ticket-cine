package cl.ticketcine.reserva.dto;

import lombok.Data;

@Data
public class SalaResponse {

    private String idSala;
    private String formato;
    private Integer capacidad;
}
