package cl.ticketcine.notificaciones.service;

import cl.ticketcine.notificaciones.dto.ColaEnvioRequest;
import cl.ticketcine.notificaciones.dto.ColaEnvioResponse;
import cl.ticketcine.notificaciones.dto.LogErrorRequest;
import cl.ticketcine.notificaciones.dto.LogErrorResponse;
import cl.ticketcine.notificaciones.dto.PlantillaRequest;
import cl.ticketcine.notificaciones.dto.PlantillaResponse;
import cl.ticketcine.notificaciones.dto.UsuariosProyeccionRequest;
import cl.ticketcine.notificaciones.dto.UsuariosProyeccionResponse;
import cl.ticketcine.notificaciones.exception.ColaEnvioNotFoundException;
import cl.ticketcine.notificaciones.exception.DuplicateResourceException;
import cl.ticketcine.notificaciones.exception.LogErrorNotFoundException;
import cl.ticketcine.notificaciones.exception.PlantillaNotFoundException;
import cl.ticketcine.notificaciones.exception.UsuariosProyeccionNotFoundException;
import cl.ticketcine.notificaciones.mapper.notificacionesMapper;
import cl.ticketcine.notificaciones.model.ColaEnvio;
import cl.ticketcine.notificaciones.model.LogError;
import cl.ticketcine.notificaciones.model.Plantilla;
import cl.ticketcine.notificaciones.model.UsuariosProyeccion;
import cl.ticketcine.notificaciones.repository.LogErrorRepository;
import cl.ticketcine.notificaciones.repository.PlantillaRepository;
import cl.ticketcine.notificaciones.repository.UsuariosProyeccionRepository;
import cl.ticketcine.notificaciones.repository.notificacionesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificacionesService {

    private final notificacionesRepository colaEnvioRepository;
    private final PlantillaRepository plantillaRepository;
    private final UsuariosProyeccionRepository usuariosProyeccionRepository;
    private final LogErrorRepository logErrorRepository;
    private final notificacionesMapper notificacionesMapper;

    @Transactional(readOnly = true)
    public List<PlantillaResponse> findAllPlantillas() {
        return notificacionesMapper.toPlantillaResponseList(plantillaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PlantillaResponse findPlantillaById(String id) {
        return notificacionesMapper.toPlantillaResponse(findPlantillaOrThrow(id));
    }

    public PlantillaResponse createPlantilla(PlantillaRequest request) {
        Objects.requireNonNull(request, "La solicitud de plantilla es obligatoria");
        if (plantillaRepository.existsById(request.getIdPlantilla())) {
            throw new DuplicateResourceException("Plantilla", request.getIdPlantilla());
        }

        Plantilla plantilla = notificacionesMapper.toPlantillaEntity(request);
        return notificacionesMapper.toPlantillaResponse(plantillaRepository.save(plantilla));
    }

    public PlantillaResponse updatePlantilla(String id, PlantillaRequest request) {
        Objects.requireNonNull(request, "La solicitud de plantilla es obligatoria");

        Plantilla plantilla = findPlantillaOrThrow(id);
        notificacionesMapper.updatePlantillaEntity(request, plantilla);
        return notificacionesMapper.toPlantillaResponse(plantillaRepository.save(plantilla));
    }

    public void deletePlantilla(String id) {
        String plantillaId = Objects.requireNonNull(id, "El id de la plantilla es obligatorio");
        if (!plantillaRepository.existsById(plantillaId)) {
            throw new PlantillaNotFoundException(plantillaId);
        }
        plantillaRepository.deleteById(plantillaId);
    }

    @Transactional(readOnly = true)
    public List<ColaEnvioResponse> findAllColaEnvios() {
        return notificacionesMapper.toColaEnvioResponseList(colaEnvioRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ColaEnvioResponse findColaEnvioById(Long id) {
        return notificacionesMapper.toColaEnvioResponse(findColaEnvioOrThrow(id));
    }

    public ColaEnvioResponse createColaEnvio(ColaEnvioRequest request) {
        Objects.requireNonNull(request, "La solicitud de cola de envío es obligatoria");

        Plantilla plantilla = findPlantillaOrThrow(request.getPlantillaId());
        ColaEnvio colaEnvio = notificacionesMapper.toColaEnvioEntity(request);
        colaEnvio.setPlantilla(plantilla);
        return notificacionesMapper.toColaEnvioResponse(colaEnvioRepository.save(colaEnvio));
    }

    public ColaEnvioResponse updateColaEnvio(Long id, ColaEnvioRequest request) {
        Objects.requireNonNull(request, "La solicitud de cola de envío es obligatoria");

        ColaEnvio colaEnvio = findColaEnvioOrThrow(id);
        Plantilla plantilla = findPlantillaOrThrow(request.getPlantillaId());

        notificacionesMapper.updateColaEnvioEntity(request, colaEnvio);
        colaEnvio.setPlantilla(plantilla);
        return notificacionesMapper.toColaEnvioResponse(colaEnvioRepository.save(colaEnvio));
    }

    public void deleteColaEnvio(Long id) {
        Long colaId = Objects.requireNonNull(id, "El id de la notificación es obligatorio");
        if (!colaEnvioRepository.existsById(colaId)) {
            throw new ColaEnvioNotFoundException(colaId);
        }
        colaEnvioRepository.deleteById(colaId);
    }

    @Transactional(readOnly = true)
    public List<UsuariosProyeccionResponse> findAllUsuariosProyeccion() {
        return notificacionesMapper.toUsuariosProyeccionResponseList(usuariosProyeccionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UsuariosProyeccionResponse findUsuariosProyeccionByEmail(String email) {
        return notificacionesMapper.toUsuariosProyeccionResponse(findUsuariosProyeccionOrThrow(email));
    }

    public UsuariosProyeccionResponse createUsuariosProyeccion(UsuariosProyeccionRequest request) {
        Objects.requireNonNull(request, "La solicitud de usuario proyección es obligatoria");
        if (usuariosProyeccionRepository.existsById(request.getEmail())) {
            throw new DuplicateResourceException("UsuariosProyeccion", request.getEmail());
        }

        UsuariosProyeccion usuariosProyeccion = notificacionesMapper.toUsuariosProyeccionEntity(request);
        return notificacionesMapper.toUsuariosProyeccionResponse(usuariosProyeccionRepository.save(usuariosProyeccion));
    }

    public UsuariosProyeccionResponse updateUsuariosProyeccion(String email, UsuariosProyeccionRequest request) {
        Objects.requireNonNull(request, "La solicitud de usuario proyección es obligatoria");

        UsuariosProyeccion usuariosProyeccion = findUsuariosProyeccionOrThrow(email);
        notificacionesMapper.updateUsuariosProyeccionEntity(request, usuariosProyeccion);
        return notificacionesMapper.toUsuariosProyeccionResponse(usuariosProyeccionRepository.save(usuariosProyeccion));
    }

    public void deleteUsuariosProyeccion(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        if (!usuariosProyeccionRepository.existsById(emailNotNull)) {
            throw new UsuariosProyeccionNotFoundException(emailNotNull);
        }
        usuariosProyeccionRepository.deleteById(emailNotNull);
    }

    @Transactional(readOnly = true)
    public List<LogErrorResponse> findAllLogs() {
        return notificacionesMapper.toLogErrorResponseList(logErrorRepository.findAll());
    }

    @Transactional(readOnly = true)
    public LogErrorResponse findLogById(Long id) {
        return notificacionesMapper.toLogErrorResponse(findLogOrThrow(id));
    }

    public LogErrorResponse createLog(LogErrorRequest request) {
        Objects.requireNonNull(request, "La solicitud de log es obligatoria");

        ColaEnvio colaEnvio = findColaEnvioOrThrow(request.getIdNotif());
        LogError logError = notificacionesMapper.toLogErrorEntity(request);
        logError.setNotificacion(colaEnvio);
        return notificacionesMapper.toLogErrorResponse(logErrorRepository.save(logError));
    }

    public LogErrorResponse updateLog(Long id, LogErrorRequest request) {
        Objects.requireNonNull(request, "La solicitud de log es obligatoria");

        LogError logError = findLogOrThrow(id);
        ColaEnvio colaEnvio = findColaEnvioOrThrow(request.getIdNotif());

        notificacionesMapper.updateLogErrorEntity(request, logError);
        logError.setNotificacion(colaEnvio);
        return notificacionesMapper.toLogErrorResponse(logErrorRepository.save(logError));
    }

    public void deleteLog(Long id) {
        Long logId = Objects.requireNonNull(id, "El id del log es obligatorio");
        if (!logErrorRepository.existsById(logId)) {
            throw new LogErrorNotFoundException(logId);
        }
        logErrorRepository.deleteById(logId);
    }

    private Plantilla findPlantillaOrThrow(String id) {
        String plantillaId = Objects.requireNonNull(id, "El id de la plantilla es obligatorio");
        return plantillaRepository.findById(plantillaId)
                .orElseThrow(() -> new PlantillaNotFoundException(plantillaId));
    }

    private ColaEnvio findColaEnvioOrThrow(Long id) {
        Long colaId = Objects.requireNonNull(id, "El id de la notificación es obligatorio");
        return colaEnvioRepository.findById(colaId)
                .orElseThrow(() -> new ColaEnvioNotFoundException(colaId));
    }

    private UsuariosProyeccion findUsuariosProyeccionOrThrow(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        return usuariosProyeccionRepository.findById(emailNotNull)
                .orElseThrow(() -> new UsuariosProyeccionNotFoundException(emailNotNull));
    }

    private LogError findLogOrThrow(Long id) {
        Long logId = Objects.requireNonNull(id, "El id del log es obligatorio");
        return logErrorRepository.findById(logId)
                .orElseThrow(() -> new LogErrorNotFoundException(logId));
    }
}
