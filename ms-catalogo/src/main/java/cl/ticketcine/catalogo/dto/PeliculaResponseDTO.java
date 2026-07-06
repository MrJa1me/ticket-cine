package cl.ticketcine.catalogo.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO de respuesta para Película con soporte HATEOAS.
 *
 * Incluye {@code _links} para navegar el CRUD.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PeliculaResponseDTO extends RepresentationModel<PeliculaResponseDTO> {

    private Integer idPelicula;
    private String nombrePelicula;
    private String categoria;
    private LocalDate fecha;
    private String estado;
    private String idioma;
    private Integer duracionMinutos;
    private String clasificacion;
    private String sinopsis;
}
