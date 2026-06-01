package cl.ticketcine.reserva.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MantenimientoResponse {

    private String idSala;
    private LocalDate ultimaFecha;
    private String tecnicoNombre;
}
