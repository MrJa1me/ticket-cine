package cl.ticketcine.usuarios.mapper;

import cl.ticketcine.usuarios.dto.PerfilRequest;
import cl.ticketcine.usuarios.dto.PerfilResponse;
import cl.ticketcine.usuarios.model.entity.Perfil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Perfil toEntity(PerfilRequest request);

    @Mapping(target = "usuarioEmail", source = "usuario.email")
    PerfilResponse toResponse(Perfil perfil);

    List<PerfilResponse> toResponseList(List<Perfil> perfiles);

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(PerfilRequest request, @MappingTarget Perfil perfil);
}