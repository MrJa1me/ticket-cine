package cl.ticketcine.horarios.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HorarioResponse {

    private Long id;
    private String pelicula;
    private String sala;
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal precio;
    private Boolean habilitado;
}
