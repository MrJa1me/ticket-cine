package cl.ticketcine.notificaciones.mapper;

import cl.ticketcine.notificaciones.dto.ColaEnvioRequest;
import cl.ticketcine.notificaciones.dto.ColaEnvioResponse;
import cl.ticketcine.notificaciones.dto.LogErrorRequest;
import cl.ticketcine.notificaciones.dto.LogErrorResponse;
import cl.ticketcine.notificaciones.dto.PlantillaRequest;
import cl.ticketcine.notificaciones.dto.PlantillaResponse;
import cl.ticketcine.notificaciones.dto.UsuariosProyeccionRequest;
import cl.ticketcine.notificaciones.dto.UsuariosProyeccionResponse;
import cl.ticketcine.notificaciones.model.ColaEnvio;
import cl.ticketcine.notificaciones.model.LogError;
import cl.ticketcine.notificaciones.model.Plantilla;
import cl.ticketcine.notificaciones.model.UsuariosProyeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface notificacionesMapper {

    @Mapping(target = "colaEnvios", ignore = true)
    Plantilla toPlantillaEntity(PlantillaRequest request);

    @Mapping(target = "colaEnvios", ignore = true)
    void updatePlantillaEntity(PlantillaRequest request, @MappingTarget Plantilla plantilla);

    PlantillaResponse toPlantillaResponse(Plantilla plantilla);

    List<PlantillaResponse> toPlantillaResponseList(List<Plantilla> plantillas);

    @Mapping(target = "plantillaId", source = "plantilla.idPlantilla")
    ColaEnvioResponse toColaEnvioResponse(ColaEnvio colaEnvio);

    List<ColaEnvioResponse> toColaEnvioResponseList(List<ColaEnvio> colaEnvios);

    @Mapping(target = "plantilla", ignore = true)
    @Mapping(target = "logs", ignore = true)
    ColaEnvio toColaEnvioEntity(ColaEnvioRequest request);

    @Mapping(target = "plantilla", ignore = true)
    @Mapping(target = "logs", ignore = true)
    void updateColaEnvioEntity(ColaEnvioRequest request, @MappingTarget ColaEnvio colaEnvio);

    UsuariosProyeccionResponse toUsuariosProyeccionResponse(UsuariosProyeccion usuariosProyeccion);

    List<UsuariosProyeccionResponse> toUsuariosProyeccionResponseList(List<UsuariosProyeccion> usuariosProyeccion);

    UsuariosProyeccion toUsuariosProyeccionEntity(UsuariosProyeccionRequest request);

    void updateUsuariosProyeccionEntity(UsuariosProyeccionRequest request, @MappingTarget UsuariosProyeccion usuariosProyeccion);

    @Mapping(target = "idNotif", source = "notificacion.idNotif")
    LogErrorResponse toLogErrorResponse(LogError logError);

    List<LogErrorResponse> toLogErrorResponseList(List<LogError> logErrors);

    @Mapping(target = "notificacion", ignore = true)
    LogError toLogErrorEntity(LogErrorRequest request);

    @Mapping(target = "notificacion", ignore = true)
    void updateLogErrorEntity(LogErrorRequest request, @MappingTarget LogError logError);
}
