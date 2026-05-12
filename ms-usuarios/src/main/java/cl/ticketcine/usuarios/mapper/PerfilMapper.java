package cl.ticketcine.usuarios.mapper;

import cl.ticketcine.usuarios.dto.PerfilRequest;
import cl.ticketcine.usuarios.dto.PerfilResponse;
import cl.ticketcine.usuarios.model.Perfil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @NonNull Perfil toEntity(PerfilRequest request);

    @Mapping(source = "usuario.email", target = "usuarioEmail")
    PerfilResponse toResponse(Perfil perfil);

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Perfil toModel(PerfilRequest request);

    List<PerfilResponse> toResponseList(List<Perfil> perfiles);

    @Mapping(target = "idPerfil", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(PerfilRequest request, @MappingTarget Perfil perfil);
}