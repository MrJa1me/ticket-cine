package cl.ticketcine.validacion.service;

import cl.ticketcine.validacion.dto.*;
import cl.ticketcine.validacion.exception.ValidacionNotFoundException;
import cl.ticketcine.validacion.mapper.ValidacionMapper;
import cl.ticketcine.validacion.model.entity.*;
import cl.ticketcine.validacion.repository.*;
import cl.ticketcine.validacion.event.ValidacionEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidacionService {

    private final PuntoControlRepository puntoControlRepository;
    private final QrCodigoRepository qrCodigoRepository;
    private final HistorialAccesoRepository historialAccesoRepository;
    private final ReservasProyeccionRepository reservasProyeccionRepository;
    private final ValidacionMapper mapper;
    private final ValidacionEventProducer eventProducer;

    public List<PuntoControlResponse> findAllPuntosControl() {
        return mapper.toPuntoControlResponseList(puntoControlRepository.findAll());
    }

    public PuntoControlResponse findPuntoControlById(String idPunto) {
        String idPuntoNotNull = Objects.requireNonNull(idPunto, "El ID del punto de control es obligatorio");
        PuntoControl punto = puntoControlRepository.findById(idPuntoNotNull)
                .orElseThrow(() -> new ValidacionNotFoundException("Punto de control no encontrado con id: " + idPuntoNotNull));
        return mapper.toPuntoControlResponse(punto);
    }

    public List<PuntoControlResponse> searchPuntosControl(String ubicacion) {
        String ubicacionNotNull = Objects.requireNonNull(ubicacion, "La ubicación es obligatoria");
        return mapper.toPuntoControlResponseList(puntoControlRepository.findByUbicacionContainingIgnoreCase(ubicacionNotNull));
    }

    @Transactional
    public PuntoControlResponse createPuntoControl(PuntoControlRequest request) {
        Objects.requireNonNull(request, "La solicitud de punto de control es obligatoria");
        if (puntoControlRepository.existsById(request.getIdPunto())) {
            throw new IllegalArgumentException("Ya existe un punto de control con id: " + request.getIdPunto());
        }
        PuntoControl punto = PuntoControl.builder()
                .idPunto(request.getIdPunto())
                .ubicacion(request.getUbicacion())
                .build();
        return mapper.toPuntoControlResponse(puntoControlRepository.save(punto));
    }

    @Transactional
    public PuntoControlResponse updatePuntoControl(String idPunto, PuntoControlRequest request) {
        String idPuntoNotNull = Objects.requireNonNull(idPunto, "El ID del punto de control es obligatorio");
        Objects.requireNonNull(request, "La solicitud de punto de control es obligatoria");
        PuntoControl existing = puntoControlRepository.findById(idPuntoNotNull)
                .orElseThrow(() -> new ValidacionNotFoundException("Punto de control no encontrado con id: " + idPuntoNotNull));

        if (!idPuntoNotNull.equals(request.getIdPunto())) {
            throw new IllegalArgumentException("El id de punto en la ruta y el cuerpo deben coincidir");
        }

        existing.setUbicacion(request.getUbicacion());
        return mapper.toPuntoControlResponse(puntoControlRepository.save(existing));
    }

    @Transactional
    public void deletePuntoControl(String idPunto) {
        String idPuntoNotNull = Objects.requireNonNull(idPunto, "El ID del punto de control es obligatorio");
        if (!puntoControlRepository.existsById(idPuntoNotNull)) {
            throw new ValidacionNotFoundException("Punto de control no encontrado con id: " + idPuntoNotNull);
        }
        puntoControlRepository.deleteById(idPuntoNotNull);
    }

    public List<QrCodigoResponse> findAllQrCodigos() {
        return mapper.toQrCodigoResponseList(qrCodigoRepository.findAll());
    }

    public QrCodigoResponse findQrCodigoById(Long idQr) {
        Long idQrNotNull = Objects.requireNonNull(idQr, "El ID del código QR es obligatorio");
        QrCodigo qr = qrCodigoRepository.findById(idQrNotNull)
                .orElseThrow(() -> new ValidacionNotFoundException("QR no encontrado con id: " + idQrNotNull));
        return mapper.toQrCodigoResponse(qr);
    }

    public List<QrCodigoResponse> findQrCodigosByReservaId(UUID reservaId) {
        UUID reservaIdNotNull = Objects.requireNonNull(reservaId, "El ID de la reserva es obligatorio");
        return mapper.toQrCodigoResponseList(qrCodigoRepository.findByReservaId(reservaIdNotNull));
    }

    @Transactional
    public QrCodigoResponse createQrCodigo(QrCodigoRequest request) {
        Objects.requireNonNull(request, "La solicitud de código QR es obligatoria");
        QrCodigo qr = QrCodigo.builder()
                .reservaId(request.getReservaId())
                .hashCode(request.getHashCode())
                .activo(request.getActivo())
                .build();
        qr = qrCodigoRepository.save(qr);
        log.info("QR creado: {}", qr.getIdQr());
        eventProducer.publishQrCreated(qr.getIdQr(), qr.getReservaId().toString(), qr.getHashCode());
        return mapper.toQrCodigoResponse(qr);
    }

    @Transactional
    public QrCodigoResponse updateQrCodigo(Long idQr, QrCodigoRequest request) {
        Long idQrNotNull = Objects.requireNonNull(idQr, "El ID del código QR es obligatorio");
        Objects.requireNonNull(request, "La solicitud de código QR es obligatoria");
        QrCodigo existing = qrCodigoRepository.findById(idQrNotNull)
                .orElseThrow(() -> new ValidacionNotFoundException("QR no encontrado con id: " + idQrNotNull));

        existing.setReservaId(request.getReservaId());
        existing.setHashCode(request.getHashCode());
        existing.setActivo(request.getActivo());

        return mapper.toQrCodigoResponse(qrCodigoRepository.save(existing));
    }

    @Transactional
    public void deleteQrCodigo(Long idQr) {
        Long idQrNotNull = Objects.requireNonNull(idQr, "El ID del código QR es obligatorio");
        if (!qrCodigoRepository.existsById(idQrNotNull)) {
            throw new ValidacionNotFoundException("QR no encontrado con id: " + idQrNotNull);
        }
        qrCodigoRepository.deleteById(idQrNotNull);
    }

    public List<HistorialAccesoResponse> findHistorialByPunto(String idPunto) {
        String idPuntoNotNull = Objects.requireNonNull(idPunto, "El ID del punto de control es obligatorio");
        return mapper.toHistorialAccesoResponseList(historialAccesoRepository.findByPuntoControl_IdPunto(idPuntoNotNull));
    }

    public List<HistorialAccesoResponse> findHistorialByQr(Long idQr) {
        Long idQrNotNull = Objects.requireNonNull(idQr, "El ID del código QR es obligatorio");
        return mapper.toHistorialAccesoResponseList(historialAccesoRepository.findByQrCodigo_IdQr(idQrNotNull));
    }

    @Transactional
    public HistorialAccesoResponse createHistorialAcceso(HistorialAccesoRequest request) {
        Objects.requireNonNull(request, "La solicitud de historial de acceso es obligatoria");
        QrCodigo qr = qrCodigoRepository.findById(request.getIdQr())
                .orElseThrow(() -> new ValidacionNotFoundException("QR no encontrado con id: " + request.getIdQr()));

        PuntoControl punto = puntoControlRepository.findById(request.getIdPunto())
                .orElseThrow(() -> new ValidacionNotFoundException("Punto de control no encontrado con id: " + request.getIdPunto()));

        HistorialAcceso acceso = HistorialAcceso.builder()
                .qrCodigo(qr)
                .puntoControl(punto)
                .build();

        acceso = historialAccesoRepository.save(acceso);
        log.info("Acceso registrado: punto={}, qr={}", punto.getIdPunto(), qr.getIdQr());
        eventProducer.publishAccesoRegistrado(punto.getIdPunto(), qr.getIdQr());
        return mapper.toHistorialAccesoResponse(acceso);
    }

    public List<ReservasProyeccionResponse> findReservasByEmail(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        return reservasProyeccionRepository.findByUserEmail(emailNotNull).stream()
                .map(mapper::toReservasProyeccionResponse)
                .toList();
    }
}
