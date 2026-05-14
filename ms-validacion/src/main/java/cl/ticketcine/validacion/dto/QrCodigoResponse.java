package cl.ticketcine.validacion.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QrCodigoResponse {

    private Long idQr;
    private UUID reservaId;
    private String hashCode;
    private Boolean activo;
    private long cantidadAccesos;
}
