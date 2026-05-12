package cl.triskeledu.catalogo.mapper;

import cl.triskeledu.catalogo.dto.LibroRequest;
import cl.triskeledu.catalogo.dto.LibroResponse;
import cl.triskeledu.catalogo.model.Libro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @NonNull Libro toEntity(LibroRequest request);

    LibroResponse toResponse(Libro libro);

    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "categorias", ignore = true)
    Libro toModel(LibroRequest request);

    List<LibroResponse> toResponseList(List<Libro> libros);

    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "categorias", ignore = true)
    void updateEntity(LibroRequest request, @MappingTarget Libro libro);
}
