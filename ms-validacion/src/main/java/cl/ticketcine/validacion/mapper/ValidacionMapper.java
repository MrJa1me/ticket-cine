package cl.ticketcine.validacion.mapper;

import cl.ticketcine.validacion.dto.*;
import cl.ticketcine.validacion.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ValidacionMapper {

    @Mapping(target = "cantidadHistorialAccesos", expression = "java(puntoControl.getHistorialAccesos() == null ? 0L : puntoControl.getHistorialAccesos().size())")
    PuntoControlResponse toPuntoControlResponse(PuntoControl puntoControl);

    @Mapping(target = "cantidadAccesos", expression = "java(qrCodigo.getHistorialAccesos() == null ? 0L : qrCodigo.getHistorialAccesos().size())")
    QrCodigoResponse toQrCodigoResponse(QrCodigo qrCodigo);

    @Mapping(target = "idQr", source = "qrCodigo.idQr")
    @Mapping(target = "idPunto", source = "puntoControl.idPunto")
    HistorialAccesoResponse toHistorialAccesoResponse(HistorialAcceso acceso);

    ReservasProyeccionResponse toReservasProyeccionResponse(ReservasProyeccion reserva);

    List<PuntoControlResponse> toPuntoControlResponseList(List<PuntoControl> puntos);

    List<QrCodigoResponse> toQrCodigoResponseList(List<QrCodigo> qrCodigos);

    List<HistorialAccesoResponse> toHistorialAccesoResponseList(List<HistorialAcceso> accesos);
}
