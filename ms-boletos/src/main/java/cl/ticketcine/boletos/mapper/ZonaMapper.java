package cl.ticketcine.boletos.mapper;

import cl.ticketcine.boletos.dto.ZonaRequest;
import cl.ticketcine.boletos.dto.ZonaResponse;
import cl.ticketcine.boletos.model.Zona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ZonaMapper {

    @Mapping(target = "idZona", ignore = true)
    Zona toEntity(ZonaRequest request);

    ZonaResponse toResponse(Zona zona);

    List<ZonaResponse> toResponseList(List<Zona> zonas);

    @Mapping(target = "idZona", ignore = true)
    void updateFromRequest(ZonaRequest request, @MappingTarget Zona zona);
}
