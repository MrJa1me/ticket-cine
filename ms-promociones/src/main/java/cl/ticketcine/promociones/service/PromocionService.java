package cl.ticketcine.promociones.service;

import cl.ticketcine.promociones.client.UsuariosClient;
import cl.ticketcine.promociones.dto.*;
import cl.ticketcine.promociones.exception.InvalidRequestException;
import cl.ticketcine.promociones.exception.NotFoundException;
import cl.ticketcine.promociones.model.entity.AplicacionPromo;
import cl.ticketcine.promociones.model.entity.Campania;
import cl.ticketcine.promociones.model.entity.Cupon;
import cl.ticketcine.promociones.model.entity.UsuarioProyeccion;
import cl.ticketcine.promociones.repository.AplicacionPromoRepository;
import cl.ticketcine.promociones.repository.CampaniaRepository;
import cl.ticketcine.promociones.repository.CuponRepository;
import cl.ticketcine.promociones.repository.UsuarioProyeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromocionService {

    private final CampaniaRepository campaniaRepository;
    private final CuponRepository cuponRepository;
    private final AplicacionPromoRepository aplicacionPromoRepository;
    private final UsuarioProyeccionRepository usuarioProyeccionRepository;
    private final UsuariosClient usuariosClient;

    public List<CampaniaResponse> findAllCampanias() {
        return campaniaRepository.findAll().stream()
                .map(this::toCampaniaResponse)
                .collect(Collectors.toList());
    }

    public CampaniaResponse findCampaniaById(Long idCamp) {
        return toCampaniaResponse(getCampaniaById(idCamp));
    }

    @Transactional
    public CampaniaResponse createCampania(CampaniaRequest request) {
        Campania campania = Campania.builder()
                .nombre(request.getNombre())
                .fechaFin(request.getFechaFin())
                .build();
        campaniaRepository.save(campania);
        return toCampaniaResponse(campania);
    }

    @Transactional
    public CampaniaResponse updateCampania(Long idCamp, CampaniaRequest request) {
        Campania campania = getCampaniaById(idCamp);
        campania.setNombre(request.getNombre());
        campania.setFechaFin(request.getFechaFin());
        campaniaRepository.save(campania);
        return toCampaniaResponse(campania);
    }

    @Transactional
    public void deleteCampania(Long idCamp) {
        Campania campania = getCampaniaById(idCamp);
        campaniaRepository.delete(campania);
    }

    public List<CuponResponse> findAllCupones() {
        return cuponRepository.findAll().stream()
                .map(this::toCuponResponse)
                .collect(Collectors.toList());
    }

    public CuponResponse findCuponByCodigo(String codigo) {
        return toCuponResponse(getCuponByCodigo(codigo));
    }

    @Transactional
    public CuponResponse createCupon(CuponRequest request) {
        if (cuponRepository.existsById(request.getCodigo())) {
            throw new InvalidRequestException("El código del cupón ya existe: " + request.getCodigo());
        }
        Campania campania = getCampaniaById(request.getIdCamp());
        Cupon cupon = Cupon.builder()
                .codigo(request.getCodigo())
                .campania(campania)
                .pctDesc(request.getPctDesc())
                .activo(request.getActivo())
                .build();
        cuponRepository.save(cupon);
        return toCuponResponse(cupon);
    }

    @Transactional
    public CuponResponse updateCupon(String codigo, CuponRequest request) {
        if (!codigo.equals(request.getCodigo())) {
            throw new InvalidRequestException("El código de la ruta y del cuerpo deben coincidir");
        }
        Cupon cupon = getCuponByCodigo(codigo);
        Campania campania = getCampaniaById(request.getIdCamp());
        cupon.setCampania(campania);
        cupon.setPctDesc(request.getPctDesc());
        cupon.setActivo(request.getActivo());
        cuponRepository.save(cupon);
        return toCuponResponse(cupon);
    }

    @Transactional
    public void deleteCupon(String codigo) {
        Cupon cupon = getCuponByCodigo(codigo);
        cuponRepository.delete(cupon);
    }

    @Transactional
    public CuponResponse deactivateCupon(String codigo) {
        Cupon cupon = getCuponByCodigo(codigo);
        cupon.setActivo(false);
        cuponRepository.save(cupon);
        return toCuponResponse(cupon);
    }

    public List<AplicacionPromoResponse> findAllAplicaciones() {
        return aplicacionPromoRepository.findAll().stream()
                .map(this::toAplicacionPromoResponse)
                .collect(Collectors.toList());
    }

    public AplicacionPromoResponse findAplicacionById(Long idApp) {
        return toAplicacionPromoResponse(getAplicacionById(idApp));
    }

    @Transactional
    public AplicacionPromoResponse applyCupon(AplicacionPromoRequest request) {
        if (!usuariosClient.existsByEmail(request.getUserEmail())) {
            throw new InvalidRequestException("El usuario no existe en ms-usuarios: " + request.getUserEmail());
        }
        Cupon cupon = getCuponByCodigo(request.getCodigoCupon());
        if (!Boolean.TRUE.equals(cupon.getActivo())) {
            throw new InvalidRequestException("El cupón no está activo: " + request.getCodigoCupon());
        }
        if (cupon.getCampania().getFechaFin().isBefore(LocalDate.now())) {
            throw new InvalidRequestException("La campaña asociada al cupón ya finalizó");
        }
        AplicacionPromo aplicacion = AplicacionPromo.builder()
                .cupon(cupon)
                .userEmail(request.getUserEmail())
                .build();
        aplicacionPromoRepository.save(aplicacion);
        return toAplicacionPromoResponse(aplicacion);
    }

    public List<UsuarioProyeccionResponse> findAllUsuariosProyeccion() {
        return usuarioProyeccionRepository.findAll().stream()
                .map(this::toUsuarioProyeccionResponse)
                .collect(Collectors.toList());
    }

    public UsuarioProyeccionResponse findUsuarioProyeccionByEmail(String email) {
        return toUsuarioProyeccionResponse(getUsuarioProyeccionByEmail(email));
    }

    @Transactional
    public UsuarioProyeccionResponse createUsuarioProyeccion(UsuarioProyeccionRequest request) {
        if (usuarioProyeccionRepository.existsById(request.getEmail())) {
            throw new InvalidRequestException("El registro de usuario-proyección ya existe: " + request.getEmail());
        }
        UsuarioProyeccion usuario = UsuarioProyeccion.builder()
                .email(request.getEmail())
                .esEstudiante(request.getEsEstudiante())
                .build();
        usuarioProyeccionRepository.save(usuario);
        return toUsuarioProyeccionResponse(usuario);
    }

    @Transactional
    public UsuarioProyeccionResponse updateUsuarioProyeccion(String email, UsuarioProyeccionRequest request) {
        if (!email.equalsIgnoreCase(request.getEmail())) {
            throw new InvalidRequestException("El email de la ruta y del cuerpo deben coincidir");
        }
        UsuarioProyeccion usuario = getUsuarioProyeccionByEmail(email);
        usuario.setEsEstudiante(request.getEsEstudiante());
        usuarioProyeccionRepository.save(usuario);
        return toUsuarioProyeccionResponse(usuario);
    }

    @Transactional
    public void deleteUsuarioProyeccion(String email) {
        UsuarioProyeccion usuario = getUsuarioProyeccionByEmail(email);
        usuarioProyeccionRepository.delete(usuario);
    }

    private Campania getCampaniaById(Long idCamp) {
        return campaniaRepository.findById(idCamp)
                .orElseThrow(() -> new NotFoundException("Campania", idCamp));
    }

    private Cupon getCuponByCodigo(String codigo) {
        return cuponRepository.findById(codigo)
                .orElseThrow(() -> new NotFoundException("Cupon", codigo));
    }

    private AplicacionPromo getAplicacionById(Long idApp) {
        return aplicacionPromoRepository.findById(idApp)
                .orElseThrow(() -> new NotFoundException("AplicacionPromo", idApp));
    }

    private UsuarioProyeccion getUsuarioProyeccionByEmail(String email) {
        return usuarioProyeccionRepository.findById(email)
                .orElseThrow(() -> new NotFoundException("UsuarioProyeccion", email));
    }

    private CampaniaResponse toCampaniaResponse(Campania campania) {
        return CampaniaResponse.builder()
                .idCamp(campania.getIdCamp())
                .nombre(campania.getNombre())
                .fechaFin(campania.getFechaFin())
                .build();
    }

    private CuponResponse toCuponResponse(Cupon cupon) {
        return CuponResponse.builder()
                .codigo(cupon.getCodigo())
                .idCamp(cupon.getCampania() != null ? cupon.getCampania().getIdCamp() : null)
                .pctDesc(cupon.getPctDesc())
                .activo(cupon.getActivo())
                .build();
    }

    private AplicacionPromoResponse toAplicacionPromoResponse(AplicacionPromo aplicacion) {
        return AplicacionPromoResponse.builder()
                .idApp(aplicacion.getIdApp())
                .codigoCupon(aplicacion.getCupon() != null ? aplicacion.getCupon().getCodigo() : null)
                .userEmail(aplicacion.getUserEmail())
                .fechaUso(aplicacion.getFechaUso())
                .build();
    }

    private UsuarioProyeccionResponse toUsuarioProyeccionResponse(UsuarioProyeccion usuario) {
        return UsuarioProyeccionResponse.builder()
                .email(usuario.getEmail())
                .esEstudiante(usuario.getEsEstudiante())
                .build();
    }
}
