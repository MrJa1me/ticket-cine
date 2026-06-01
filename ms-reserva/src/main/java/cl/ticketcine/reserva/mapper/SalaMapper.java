package cl.ticketcine.reserva.mapper;

import cl.ticketcine.reserva.dto.SalaRequest;
import cl.ticketcine.reserva.dto.SalaResponse;
import cl.ticketcine.reserva.model.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaMapper {

    Sala toEntity(SalaRequest request);

    SalaResponse toResponse(Sala sala);

    List<SalaResponse> toResponseList(List<Sala> salas);

    void updateEntity(SalaRequest request, @MappingTarget Sala sala);
}
