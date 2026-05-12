package cl.ticketcine.horarios.mapper;

import cl.ticketcine.horarios.dto.HorarioRequest;
import cl.ticketcine.horarios.dto.HorarioResponse;
import cl.ticketcine.horarios.model.Horario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HorarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habilitado", ignore = true)
    Horario toEntity(HorarioRequest request);

    HorarioResponse toResponse(Horario horario);

    List<HorarioResponse> toResponseList(List<Horario> horarios);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habilitado", ignore = true)
    void updateEntity(HorarioRequest request, @MappingTarget Horario horario);
}
