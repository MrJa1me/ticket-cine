package cl.ticketcine.usuarios.mapper;

import cl.ticketcine.usuarios.dto.UsuarioRequest;
import cl.ticketcine.usuarios.dto.UsuarioResponse;
import cl.ticketcine.usuarios.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "membresia", ignore = true)
    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDate.now())")
    Usuario toEntity(UsuarioRequest request);

    UsuarioResponse toResponse(Usuario usuario);

    List<UsuarioResponse> toResponseList(List<Usuario> usuarios);

    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "membresia", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    void updateEntity(UsuarioRequest request, @MappingTarget Usuario usuario);
}