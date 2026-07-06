package cl.ticketcine.catalogo.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PeliculaRequestDTO {
    @NotBlank(message = "El nombre de la Película es obligatorio")
    @Size(min = 3, max = 100)
    private String nombrePelicula;
    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
    private String idioma;
    @NotNull(message = "La duración es obligatoria")
    private Integer duracionMinutos;
    private String clasificacion;
    @Size(max = 500, message = "La sinopsis no puede exceder los 500 caracteres")
    private String sinopsis;
    private Integer idGenero;
}
