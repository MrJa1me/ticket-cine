package cl.ticketcine.compras.mapper;

import cl.ticketcine.compras.dto.CompraRequest;
import cl.ticketcine.compras.dto.CompraResponse;
import cl.ticketcine.compras.dto.DetalleCompraRequest;
import cl.ticketcine.compras.dto.DetalleCompraResponse;
import cl.ticketcine.compras.model.Compra;
import cl.ticketcine.compras.model.DetalleCompra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompraMapper {
    // 1. Mapeo principal de la Compra
    CompraResponse toResponse(Compra compra);

    @Mapping(target = "idCompra", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "total", ignore = true)
    Compra toEntity(CompraRequest request);

    // 2. Mapeos individuales de los Detalles
    DetalleCompraResponse toDetalleResponse(DetalleCompra detalle);

    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    DetalleCompra toDetalleEntity(DetalleCompraRequest detalleRequest);
}
