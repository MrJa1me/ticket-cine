package cl.ticketcine.pagos.mapper;

import cl.ticketcine.pagos.dto.PagosRequest;
import cl.ticketcine.pagos.dto.PagosResponse;
import cl.ticketcine.pagos.model.MetodoPago;
import cl.ticketcine.pagos.model.Transaccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagosMapper {

    @Mapping(target = "idTx", ignore = true)
    @Mapping(target = "fechaTx", ignore = true)
    @Mapping(target = "metodoPago", source = "metodoId")
    @Mapping(target = "reembolsos", ignore = true)
    @NonNull Transaccion toEntity(PagosRequest request);

    @Mapping(target = "metodoId", source = "metodoPago.idMetodo")
    PagosResponse toResponse(Transaccion transaccion);

    List<PagosResponse> toResponseList(List<Transaccion> transacciones);

    @Mapping(target = "idTx", ignore = true)
    @Mapping(target = "fechaTx", ignore = true)
    @Mapping(target = "metodoPago", source = "metodoId")
    @Mapping(target = "reembolsos", ignore = true)
    void updateEntity(PagosRequest request, @MappingTarget Transaccion transaccion);

    default MetodoPago map(String id) {
        if (id == null) return null;
        MetodoPago m = new MetodoPago();
        m.setIdMetodo(id);
        return m;
    }
}
