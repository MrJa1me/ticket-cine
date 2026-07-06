package cl.ticketcine.compras.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class DetalleCompraResponse {
    private Integer idDetalle;
    private Integer idBoleto;
    private Integer cantidad;
    private Integer subtotal;

}
