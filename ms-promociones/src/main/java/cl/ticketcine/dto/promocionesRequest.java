package cl.ticketcine.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PromocionesRequest {

    @NotBlank(message = "El nombre de la promoción es obligatorio")
    @Size(max = 100, message = "El nombre de la promoción no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción de la promoción es obligatoria")
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 0, message = "El porcentaje de descuento no puede ser menor a 0")
    @Max(value = 100, message = "El porcentaje de descuento no puede ser mayor a 100")
    private Integer pctDesc;

    @NotNull(message = "El estado de la promoción es obligatorio")
    private Boolean activo;
}
