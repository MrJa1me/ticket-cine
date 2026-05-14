package cl.ticketcine.validacion.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistorialAccesoResponse {

    private Long idAcc;
    private Long idQr;
    private String idPunto;
    private LocalDateTime timestampIngreso;
}
