package cl.ticketcine.reserva.mapper;

import cl.ticketcine.reserva.dto.MantenimientoRequest;
import cl.ticketcine.reserva.dto.MantenimientoResponse;
import cl.ticketcine.reserva.model.Mantenimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MantenimientoMapper {

    @Mapping(target = "idMaint", ignore = true)
    @Mapping(target = "sala", ignore = true)
    Mantenimiento toEntity(MantenimientoRequest request);

    @Mapping(source = "sala.idSala", target = "idSala")
    MantenimientoResponse toResponse(Mantenimiento mantenimiento);

    List<MantenimientoResponse> toResponseList(List<Mantenimiento> mantenimientos);

    @Mapping(target = "idMaint", ignore = true)
    @Mapping(target = "sala", ignore = true)
    void updateEntity(MantenimientoRequest request, @MappingTarget Mantenimiento mantenimiento);
}
