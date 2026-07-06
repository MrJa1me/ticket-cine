package cl.ticketcine.catalogo.mapper;

import cl.ticketcine.catalogo.model.Pelicula;
import cl.ticketcine.catalogo.dto.PeliculaRequestDTO;
import cl.ticketcine.catalogo.dto.PeliculaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PeliculaMapper {

    PeliculaResponseDTO toResponseDTO(Pelicula entity);

    @Mapping(target = "idPelicula", ignore = true)
    Pelicula toEntity(PeliculaRequestDTO request);

    List<PeliculaResponseDTO> toResponseList(List<Pelicula> Peliculas);

    void updateFromRequest(PeliculaRequestDTO request, @MappingTarget Pelicula entity);
}
