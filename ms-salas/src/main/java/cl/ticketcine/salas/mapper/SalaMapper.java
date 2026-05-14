package cl.ticketcine.salas.mapper;

import cl.ticketcine.salas.dto.SalaRequest;
import cl.ticketcine.salas.dto.SalaResponse;
import cl.ticketcine.salas.model.entity.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaMapper {

    @Mapping(target = "asientos", ignore = true)
    @Mapping(target = "mantenimientos", ignore = true)
    Sala toEntity(SalaRequest request);

    @Mapping(target = "cantidadAsientos", expression = "java(sala.getAsientos() == null ? 0L : sala.getAsientos().size())")
    @Mapping(target = "cantidadMantenimientos", expression = "java(sala.getMantenimientos() == null ? 0L : sala.getMantenimientos().size())")
    SalaResponse toResponse(Sala sala);

    List<SalaResponse> toResponseList(List<Sala> salas);
}
