package cl.ticketcine.busqueda.mapper;

import cl.ticketcine.busqueda.dto.CategoriaResponse;
import cl.ticketcine.busqueda.dto.PeliculaResponse;
import cl.ticketcine.busqueda.dto.UsuarioProyeccionResponse;
import cl.ticketcine.busqueda.model.entity.Categoria;
import cl.ticketcine.busqueda.model.entity.Pelicula;
import cl.ticketcine.busqueda.model.entity.UsuarioProyeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BusquedaMapper {

    @Mapping(target = "cantidadPeliculas", expression = "java(categoria.getPeliculas() == null ? 0L : categoria.getPeliculas().size())")
    CategoriaResponse toCategoriaResponse(Categoria categoria);

    @Mapping(target = "idCat", source = "categoria.idCat")
    @Mapping(target = "categoriaNombre", source = "categoria.nombre")
    @Mapping(target = "cantidadActores", expression = "java(pelicula.getElenco() == null ? 0L : pelicula.getElenco().size())")
    PeliculaResponse toPeliculaResponse(Pelicula pelicula);

    UsuarioProyeccionResponse toUsuarioProyeccionResponse(UsuarioProyeccion usuario);

    List<CategoriaResponse> toCategoriaResponseList(List<Categoria> categorias);

    List<PeliculaResponse> toPeliculaResponseList(List<Pelicula> peliculas);
}
