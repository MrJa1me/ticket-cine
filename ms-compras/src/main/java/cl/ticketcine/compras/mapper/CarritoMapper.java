package cl.ticketcine.compras.mapper;

import cl.ticketcine.compras.dto.CarritoResponse;
import cl.ticketcine.compras.model.Carrito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarritoMapper {
    @Mapping(source = "total", target = "totalEstimado")
    CarritoResponse toResponse(Carrito carrito);

}
