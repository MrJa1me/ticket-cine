package cl.ticketcine.usuarios.mapper;

import cl.ticketcine.usuarios.dto.UsuarioRequest;
import cl.ticketcine.usuarios.dto.UsuarioResponse;
import cl.ticketcine.usuarios.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "membresias", ignore = true)
    @NonNull Usuario toEntity(UsuarioRequest request);

    UsuarioResponse toResponse(Usuario usuario);

    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "membresias", ignore = true)
    Usuario toModel(UsuarioRequest request);

    List<UsuarioResponse> toResponseList(List<Usuario> usuarios);

    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "membresias", ignore = true)
    void updateEntity(UsuarioRequest request, @MappingTarget Usuario usuario);
}