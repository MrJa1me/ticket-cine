package cl.ticketcine.usuarios.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MembresiaResponse {

    private Integer idMembresia;
    private String usuarioEmail;
    private String nivel;
    private Integer puntosAcumulados;
    private LocalDate fechaVencimiento;
}