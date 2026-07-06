package cl.ticketcine.boletos.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO de respuesta para Boleto con soporte HATEOAS.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BoletoResponse extends RepresentationModel<BoletoResponse> {

    private Integer idBoleto;
    private Integer idPelicula;
    private Integer idZona;
    private String codigo;
    private String tipo;
    private String estado;
    private LocalDate fechaEmision;
}
