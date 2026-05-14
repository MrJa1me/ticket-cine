package cl.ticketcine.salas.dto;

import lombok.Data;

@Data
public class SalaResponse {

    private String idSala;
    private String formato;
    private Integer capacidad;
    private long cantidadAsientos;
    private long cantidadMantenimientos;
}
