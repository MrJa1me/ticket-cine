package cl.ticketcine.service;

import cl.ticketcine.dto.AplicacionPromoRequest;
import cl.ticketcine.dto.AplicacionPromoResponse;
import cl.ticketcine.dto.CampaniaRequest;
import cl.ticketcine.dto.CampaniaResponse;
import cl.ticketcine.dto.CuponRequest;
import cl.ticketcine.dto.CuponResponse;
import cl.ticketcine.dto.UsuariosProyeccionRequest;
import cl.ticketcine.dto.UsuariosProyeccionResponse;
import cl.ticketcine.exception.AplicacionPromoNotFoundException;
import cl.ticketcine.exception.CampaniaNotFoundException;
import cl.ticketcine.exception.CuponNotFoundException;
import cl.ticketcine.exception.DuplicateResourceException;
import cl.ticketcine.exception.UsuariosProyeccionNotFoundException;
import cl.ticketcine.mapper.promocionesMapper;
import cl.ticketcine.model.AplicacionPromo;
import cl.ticketcine.model.Campania;
import cl.ticketcine.model.Cupon;
import cl.ticketcine.model.UsuariosProyeccion;
import cl.ticketcine.repository.AplicacionPromoRepository;
import cl.ticketcine.repository.CampaniaRepository;
import cl.ticketcine.repository.CuponRepository;
import cl.ticketcine.repository.UsuariosProyeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class promocionesService {

    private final CampaniaRepository campaniaRepository;
    private final CuponRepository cuponRepository;
    private final AplicacionPromoRepository aplicacionPromoRepository;
    private final UsuariosProyeccionRepository usuariosProyeccionRepository;
    private final promocionesMapper promocionesMapper;

    public List<CampaniaResponse> findAllCampanias() {
        return promocionesMapper.toCampaniaResponseList(campaniaRepository.findAll());
    }

    public CampaniaResponse findCampaniaById(Integer id) {
        return promocionesMapper.toCampaniaResponse(findCampaniaOrThrow(id));
    }

    public CampaniaResponse createCampania(CampaniaRequest request) {
        Objects.requireNonNull(request, "La solicitud de campaña es obligatoria");

        Campania campania = promocionesMapper.toCampaniaEntity(request);
        return promocionesMapper.toCampaniaResponse(campaniaRepository.save(campania));
    }

    public CampaniaResponse updateCampania(Integer id, CampaniaRequest request) {
        Objects.requireNonNull(request, "La solicitud de campaña es obligatoria");

        Campania campania = findCampaniaOrThrow(id);
        promocionesMapper.updateCampaniaEntity(request, campania);
        return promocionesMapper.toCampaniaResponse(campaniaRepository.save(campania));
    }

    public void deleteCampania(Integer id) {
        Integer campaniaId = Objects.requireNonNull(id, "El id de la campaña es obligatorio");
        if (!campaniaRepository.existsById(campaniaId)) {
            throw new CampaniaNotFoundException(campaniaId);
        }
        campaniaRepository.deleteById(campaniaId);
    }

    public List<CuponResponse> findAllCupones() {
        return promocionesMapper.toCuponResponseList(cuponRepository.findAll());
    }

    public CuponResponse findCuponByCodigo(String codigo) {
        return promocionesMapper.toCuponResponse(findCuponOrThrow(codigo));
    }

    public CuponResponse createCupon(CuponRequest request) {
        Objects.requireNonNull(request, "La solicitud de cupón es obligatoria");
        if (cuponRepository.existsById(request.getCodigo())) {
            throw new DuplicateResourceException("Cupon", request.getCodigo());
        }

        Cupon cupon = promocionesMapper.toCuponEntity(request);
        cupon.setCampania(findCampaniaOrThrow(request.getIdCamp()));
        return promocionesMapper.toCuponResponse(cuponRepository.save(cupon));
    }

    public CuponResponse updateCupon(String codigo, CuponRequest request) {
        Objects.requireNonNull(request, "La solicitud de cupón es obligatoria");

        Cupon cupon = findCuponOrThrow(codigo);
        if (!cupon.getCodigo().equals(request.getCodigo()) && cuponRepository.existsById(request.getCodigo())) {
            throw new DuplicateResourceException("Cupon", request.getCodigo());
        }

        promocionesMapper.updateCuponEntity(request, cupon);
        cupon.setCampania(findCampaniaOrThrow(request.getIdCamp()));
        return promocionesMapper.toCuponResponse(cuponRepository.save(cupon));
    }

    public void deleteCupon(String codigo) {
        String cupCode = Objects.requireNonNull(codigo, "El código del cupón es obligatorio");
        if (!cuponRepository.existsById(cupCode)) {
            throw new CuponNotFoundException(cupCode);
        }
        cuponRepository.deleteById(cupCode);
    }

    public List<AplicacionPromoResponse> findAllAplicaciones() {
        return promocionesMapper.toAplicacionPromoResponseList(aplicacionPromoRepository.findAll());
    }

    public AplicacionPromoResponse findAplicacionById(Integer id) {
        return promocionesMapper.toAplicacionPromoResponse(findAplicacionOrThrow(id));
    }

    public AplicacionPromoResponse createAplicacion(AplicacionPromoRequest request) {
        Objects.requireNonNull(request, "La solicitud de aplicación es obligatoria");

        AplicacionPromo aplicacion = promocionesMapper.toAplicacionPromoEntity(request);
        aplicacion.setCupon(findCuponOrThrow(request.getCodigoCupon()));
        aplicacion.setFechaUso(LocalDateTime.now());
        return promocionesMapper.toAplicacionPromoResponse(aplicacionPromoRepository.save(aplicacion));
    }

    public AplicacionPromoResponse updateAplicacion(Integer id, AplicacionPromoRequest request) {
        Objects.requireNonNull(request, "La solicitud de aplicación es obligatoria");

        AplicacionPromo aplicacion = findAplicacionOrThrow(id);
        promocionesMapper.updateAplicacionPromoEntity(request, aplicacion);
        aplicacion.setCupon(findCuponOrThrow(request.getCodigoCupon()));
        return promocionesMapper.toAplicacionPromoResponse(aplicacionPromoRepository.save(aplicacion));
    }

    public void deleteAplicacion(Integer id) {
        Integer aplicacionId = Objects.requireNonNull(id, "El id de la aplicación es obligatorio");
        if (!aplicacionPromoRepository.existsById(aplicacionId)) {
            throw new AplicacionPromoNotFoundException(aplicacionId);
        }
        aplicacionPromoRepository.deleteById(aplicacionId);
    }

    public List<UsuariosProyeccionResponse> findAllUsuariosProyeccion() {
        return promocionesMapper.toUsuariosProyeccionResponseList(usuariosProyeccionRepository.findAll());
    }

    public UsuariosProyeccionResponse findUsuariosProyeccionByEmail(String email) {
        return promocionesMapper.toUsuariosProyeccionResponse(findUsuariosProyeccionOrThrow(email));
    }

    public UsuariosProyeccionResponse createUsuariosProyeccion(UsuariosProyeccionRequest request) {
        Objects.requireNonNull(request, "La solicitud de usuario de proyección es obligatoria");
        if (usuariosProyeccionRepository.existsById(request.getEmail())) {
            throw new DuplicateResourceException("UsuariosProyeccion", request.getEmail());
        }

        UsuariosProyeccion usuariosProyeccion = promocionesMapper.toUsuariosProyeccionEntity(request);
        return promocionesMapper.toUsuariosProyeccionResponse(usuariosProyeccionRepository.save(usuariosProyeccion));
    }

    public UsuariosProyeccionResponse updateUsuariosProyeccion(String email, UsuariosProyeccionRequest request) {
        Objects.requireNonNull(request, "La solicitud de usuario de proyección es obligatoria");

        UsuariosProyeccion usuariosProyeccion = findUsuariosProyeccionOrThrow(email);
        promocionesMapper.updateUsuariosProyeccionEntity(request, usuariosProyeccion);
        return promocionesMapper.toUsuariosProyeccionResponse(usuariosProyeccionRepository.save(usuariosProyeccion));
    }

    public void deleteUsuariosProyeccion(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        if (!usuariosProyeccionRepository.existsById(emailNotNull)) {
            throw new UsuariosProyeccionNotFoundException(emailNotNull);
        }
        usuariosProyeccionRepository.deleteById(emailNotNull);
    }

    private Campania findCampaniaOrThrow(Integer id) {
        Integer campaniaId = Objects.requireNonNull(id, "El id de la campaña es obligatorio");
        return campaniaRepository.findById(campaniaId)
                .orElseThrow(() -> new CampaniaNotFoundException(campaniaId));
    }

    private Cupon findCuponOrThrow(String codigo) {
        String cupCode = Objects.requireNonNull(codigo, "El código del cupón es obligatorio");
        return cuponRepository.findById(cupCode)
                .orElseThrow(() -> new CuponNotFoundException(cupCode));
    }

    private AplicacionPromo findAplicacionOrThrow(Integer id) {
        Integer aplicacionId = Objects.requireNonNull(id, "El id de la aplicación es obligatorio");
        return aplicacionPromoRepository.findById(aplicacionId)
                .orElseThrow(() -> new AplicacionPromoNotFoundException(aplicacionId));
    }

    private UsuariosProyeccion findUsuariosProyeccionOrThrow(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        return usuariosProyeccionRepository.findById(emailNotNull)
                .orElseThrow(() -> new UsuariosProyeccionNotFoundException(emailNotNull));
    }
}
