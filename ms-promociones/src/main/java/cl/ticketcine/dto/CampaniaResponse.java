package cl.ticketcine.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CampaniaResponse {

    private Integer idCamp;
    private String nombre;
    private LocalDate fechaFin;
}