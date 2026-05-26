package cl.ticketcine.reserva.mapper;

import cl.ticketcine.reserva.dto.salaRequest;
import cl.ticketcine.reserva.dto.salaResponse;
import cl.ticketcine.reserva.model.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaMapper {

    Sala toEntity(salaRequest request);

    salaResponse toResponse(Sala sala);

    List<salaResponse> toResponseList(List<Sala> salas);

    @Mapping(target = "idSala", ignore = true)
    void updateEntity(salaRequest request, @MappingTarget Sala sala);
}
