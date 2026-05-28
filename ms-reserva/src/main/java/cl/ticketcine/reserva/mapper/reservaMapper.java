package cl.ticketcine.reserva.mapper;

import cl.ticketcine.reserva.dto.ReservaRequest;
import cl.ticketcine.reserva.dto.ReservaResponse;
import cl.ticketcine.reserva.model.Asiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(target = "idAsiento", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @NonNull Asiento toEntity(ReservaRequest request);

    @Mapping(source = "sala.idSala", target = "idSala")
    ReservaResponse toResponse(Asiento asiento);

    @Mapping(target = "idAsiento", ignore = true)
    @Mapping(target = "sala", ignore = true)
    Asiento toModel(ReservaRequest request);

    List<ReservaResponse> toResponseList(List<Asiento> asientos);

    @Mapping(target = "idAsiento", ignore = true)
    @Mapping(target = "sala", ignore = true)
    void updateEntity(ReservaRequest request, @MappingTarget Asiento asiento);
}
