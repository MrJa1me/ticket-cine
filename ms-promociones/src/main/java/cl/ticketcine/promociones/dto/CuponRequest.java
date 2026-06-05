package cl.ticketcine.promociones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class CuponRequest {

    @NotBlank(message = "El codigo del cupón es obligatorio")
    private String codigo;

    @NotNull(message = "La campaña del cupón es obligatoria")
    private Long idCamp;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Positive(message = "El porcentaje de descuento debe ser mayor a 0")
    private Integer pctDesc;

    @NotNull(message = "El estado activo del cupón es obligatorio")
    private Boolean activo;
}
