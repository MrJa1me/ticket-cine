package cl.ticketcine.usuarios.mapper;

import cl.ticketcine.usuarios.dto.MembresiaRequest;
import cl.ticketcine.usuarios.dto.MembresiaResponse;
import cl.ticketcine.usuarios.model.Membresia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MembresiaMapper {

    @Mapping(target = "idMembresia", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @NonNull Membresia toEntity(MembresiaRequest request);

    @Mapping(source = "usuario.email", target = "usuarioEmail")
    MembresiaResponse toResponse(Membresia membresia);

    @Mapping(target = "idMembresia", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Membresia toModel(MembresiaRequest request);

    List<MembresiaResponse> toResponseList(List<Membresia> membresias);

    @Mapping(target = "idMembresia", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(MembresiaRequest request, @MappingTarget Membresia membresia);
}