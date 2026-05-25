package cl.ticketcine.mapper;

import cl.ticketcine.dto.AplicacionPromoRequest;
import cl.ticketcine.dto.AplicacionPromoResponse;
import cl.ticketcine.dto.CampaniaRequest;
import cl.ticketcine.dto.CampaniaResponse;
import cl.ticketcine.dto.CuponRequest;
import cl.ticketcine.dto.CuponResponse;
import cl.ticketcine.dto.UsuariosProyeccionRequest;
import cl.ticketcine.dto.UsuariosProyeccionResponse;
import cl.ticketcine.model.AplicacionPromo;
import cl.ticketcine.model.Campania;
import cl.ticketcine.model.Cupon;
import cl.ticketcine.model.UsuariosProyeccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface promocionesMapper {

    Campania toCampaniaEntity(CampaniaRequest request);

    CampaniaResponse toCampaniaResponse(Campania campania);

    List<CampaniaResponse> toCampaniaResponseList(List<Campania> campanias);

    void updateCampaniaEntity(CampaniaRequest request, @MappingTarget Campania campania);

    @Mapping(target = "campania", ignore = true)
    Cupon toCuponEntity(CuponRequest request);

    @Mapping(target = "idCamp", source = "campania.idCamp")
    CuponResponse toCuponResponse(Cupon cupon);

    List<CuponResponse> toCuponResponseList(List<Cupon> cupones);

    @Mapping(target = "campania", ignore = true)
    void updateCuponEntity(CuponRequest request, @MappingTarget Cupon cupon);

    @Mapping(target = "cupon", ignore = true)
    @Mapping(target = "fechaUso", ignore = true)
    AplicacionPromo toAplicacionPromoEntity(AplicacionPromoRequest request);

    @Mapping(target = "codigoCupon", source = "cupon.codigo")
    AplicacionPromoResponse toAplicacionPromoResponse(AplicacionPromo aplicacionPromo);

    List<AplicacionPromoResponse> toAplicacionPromoResponseList(List<AplicacionPromo> aplicaciones);

    @Mapping(target = "cupon", ignore = true)
    @Mapping(target = "fechaUso", ignore = true)
    void updateAplicacionPromoEntity(AplicacionPromoRequest request, @MappingTarget AplicacionPromo aplicacionPromo);

    UsuariosProyeccion toUsuariosProyeccionEntity(UsuariosProyeccionRequest request);

    UsuariosProyeccionResponse toUsuariosProyeccionResponse(UsuariosProyeccion usuariosProyeccion);

    List<UsuariosProyeccionResponse> toUsuariosProyeccionResponseList(List<UsuariosProyeccion> usuarios);

    void updateUsuariosProyeccionEntity(UsuariosProyeccionRequest request, @MappingTarget UsuariosProyeccion usuariosProyeccion);
}
