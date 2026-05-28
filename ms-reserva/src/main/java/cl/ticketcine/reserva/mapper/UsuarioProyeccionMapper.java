package cl.ticketcine.reserva.mapper;

@Mapper(componentModel = "spring")
public interface UsuarioProyeccionMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "nombre", source = "nombre")
    UsuarioProyeccionResponse toResponse(UsuarioProyeccion usuarioProyeccion);

    List<UsuarioProyeccionResponse> toResponseList(List<UsuarioProyeccion> usuarioroyecciones);

}
