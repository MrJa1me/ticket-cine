package cl.ticketcine.validacion.dto;

import lombok.Data;

@Data
public class PuntoControlResponse {

    private String idPunto;
    private String ubicacion;
    private long cantidadHistorialAccesos;
}
