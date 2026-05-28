package cl.ticketcine.reserva.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.ticketcine.reserva.dto.UsuarioProyeccionResponse;
import cl.ticketcine.reserva.model.UsuarioProyeccion;

@Mapper(componentModel = "spring")
public interface UsuarioProyeccionMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "nombre", source = "nombre")
    UsuarioProyeccionResponse toResponse(UsuarioProyeccion usuarioProyeccion);

    List<UsuarioProyeccionResponse> toResponseList(List<UsuarioProyeccion> usuarioroyecciones);

}
