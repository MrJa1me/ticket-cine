package cl.ticketcine.dto;

import lombok.Data;

@Data
public class CuponResponse {

    private String codigo;
    private Integer idCamp;
    private Integer pctDesc;
    private Boolean activo;
}