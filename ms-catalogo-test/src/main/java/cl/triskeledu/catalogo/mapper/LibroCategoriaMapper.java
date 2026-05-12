package cl.triskeledu.catalogo.mapper;

import cl.triskeledu.catalogo.model.Categoria;
import cl.triskeledu.catalogo.model.Libro;
import cl.triskeledu.catalogo.model.LibroCategoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LibroCategoriaMapper {

    @Mapping(target = "id", ignore = true)
    LibroCategoria toEntity(Libro libro, Categoria categoria);
}
