package cl.ticketcine.usuarios.mapper;

import cl.ticketcine.usuarios.dto.MembresiaRequest;
import cl.ticketcine.usuarios.dto.MembresiaResponse;
import cl.ticketcine.usuarios.model.entity.Membresia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MembresiaMapper {

    @Mapping(target = "idMembresia", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Membresia toEntity(MembresiaRequest request);

    @Mapping(target = "usuarioEmail", source = "usuario.email")
    MembresiaResponse toResponse(Membresia membresia);

    List<MembresiaResponse> toResponseList(List<Membresia> membresias);

    @Mapping(target = "idMembresia", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(MembresiaRequest request, @MappingTarget Membresia membresia);
}