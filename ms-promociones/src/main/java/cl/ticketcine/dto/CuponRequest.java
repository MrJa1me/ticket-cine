package cl.ticketcine.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CuponRequest {

    @NotBlank(message = "El código del cupón es obligatorio")
    @Size(max = 20, message = "El código del cupón no puede superar los 20 caracteres")
    private String codigo;

    @NotNull(message = "El id de la campaña es obligatorio")
    private Integer idCamp;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 0, message = "El porcentaje de descuento no puede ser menor a 0")
    @Max(value = 100, message = "El porcentaje de descuento no puede ser mayor a 100")
    private Integer pctDesc;

    @NotNull(message = "El estado del cupón es obligatorio")
    private Boolean activo;
}