package cl.ticketcine.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembresiaResponse {

    private Integer idMembresia;
    private String usuarioEmail;
    private String nivel;
    private Integer puntosAcumulados;
    private LocalDate fechaVencimiento;
}