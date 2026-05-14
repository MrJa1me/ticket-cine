package cl.ticketcine.reserva.mapper;

import cl.ticketcine.reserva.dto.reservaRequest;
import cl.ticketcine.reserva.dto.reservaResponse;
import cl.ticketcine.reserva.model.Asiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper(componentModel = "spring")
public interface reservaMapper {

    @Mapping(target = "idAsiento", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @NonNull Asiento toEntity(reservaRequest request);

    @Mapping(source = "sala.idSala", target = "idSala")
    reservaResponse toResponse(Asiento asiento);

    @Mapping(target = "idAsiento", ignore = true)
    @Mapping(target = "sala", ignore = true)
    Asiento toModel(reservaRequest request);

    List<reservaResponse> toResponseList(List<Asiento> asientos);

    @Mapping(target = "idAsiento", ignore = true)
    @Mapping(target = "sala", ignore = true)
    void updateEntity(reservaRequest request, @MappingTarget Asiento asiento);
}
