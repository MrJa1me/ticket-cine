package cl.ticketcine.boletos.mapper;

import cl.ticketcine.boletos.dto.ProyPeliculaResponse;
import cl.ticketcine.boletos.model.ProyPelicula;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProyPeliculaMapper {

    // Convierte la entidad a DTO de respuesta
    ProyPeliculaResponse toResponse(ProyPelicula proyPelicula);

    // Convierte una lista de entidades a una lista de DTOs
    List<ProyPeliculaResponse> toResponseList(List<ProyPelicula> proyPeliculas);
}
