package cl.ticketcine.busqueda.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PeliculaRequest {

    @NotBlank(message = "El slug de la película es obligatorio")
    @Size(max = 100, message = "El slug no puede superar los 100 caracteres")
    private String slug;

    @NotBlank(message = "El título de la película es obligatorio")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    private String titulo;

    @NotNull(message = "El id de categoría es obligatorio")
    private Integer idCat;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos de 1 minuto")
    @Max(value = 999, message = "La duración no puede superar los 999 minutos")
    private Integer duracionMin;

    @NotNull(message = "El año de estreno es obligatorio")
    @Min(value = 1900, message = "El año de estreno debe ser posterior a 1900")
    @Max(value = 2100, message = "El año de estreno no puede ser mayor a 2100")
    private Integer estrenoAnio;
}
