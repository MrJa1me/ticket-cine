package cl.ticketcine.reserva.mapper;

import cl.ticketcine.reserva.dto.mantenimientoRequest;
import cl.ticketcine.reserva.dto.mantenimientoResponse;
import cl.ticketcine.reserva.model.Mantenimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MantenimientoMapper {

    @Mapping(target = "idMaint", ignore = true)
    @Mapping(target = "sala", ignore = true)
    Mantenimiento toEntity(mantenimientoRequest request);

    @Mapping(source = "sala.idSala", target = "idSala")
    mantenimientoResponse toResponse(Mantenimiento mantenimiento);

    List<mantenimientoResponse> toResponseList(List<Mantenimiento> mantenimientos);

    @Mapping(target = "idMaint", ignore = true)
    @Mapping(target = "sala", ignore = true)
    void updateEntity(mantenimientoRequest request, @MappingTarget Mantenimiento mantenimiento);
}
