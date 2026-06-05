package cl.ticketcine.promociones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuponResponse {

    private String codigo;
    private Long idCamp;
    private Integer pctDesc;
    private Boolean activo;
}
