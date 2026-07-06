package cl.ticketcine.boletos.mapper;

import cl.ticketcine.boletos.dto.BoletoRequest;
import cl.ticketcine.boletos.dto.BoletoResponse;
import cl.ticketcine.boletos.model.Boleto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
// @Mapper le dice a MapStruct que genere el c�digo
public interface BoletoMapper {

    // 1. Request -> Entidad (Para Guardar)
    // Indicamos que los IDs planos del DTO inicialicen los IDs de los objetos relacionados
    @Mapping(target = "idBoleto", ignore = true)
    @Mapping(target = "fechaEmision", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "pelicula.idPelicula", source = "idPelicula")
    @Mapping(target = "zona.idZona", source = "idZona")
    Boleto toEntity(BoletoRequest request);

    // 2. Entidad -> Response (Para responder al cliente)
    // Extraemos el ID num�rico desde los objetos de relaci�n de la Entidad hacia el DTO plano
    @Mapping(target = "idPelicula", source = "pelicula.idPelicula")
    @Mapping(target = "idZona", source = "zona.idZona")
    BoletoResponse toResponse(Boleto boleto);

    // Reutiliza las reglas de 'toResponse' autom�ticamente
    List<BoletoResponse> toResponseList(List<Boleto> boletos);

    // 3. Actualizaci�n de una entidad existente
    @Mapping(target = "idBoleto", ignore = true)
    @Mapping(target = "fechaEmision", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "pelicula.idPelicula", source = "idPelicula")
    @Mapping(target = "zona.idZona", source = "idZona")
    void updateFromRequest(BoletoRequest request, @MappingTarget Boleto boleto);
}
