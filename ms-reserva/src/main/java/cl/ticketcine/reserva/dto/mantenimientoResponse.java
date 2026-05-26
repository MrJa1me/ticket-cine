package cl.ticketcine.reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class mantenimientoResponse {

    private Integer idMaint;
    private String idSala;
    private LocalDate ultimaFecha;
    private String tecnicoNombre;
}
