package cl.ticketcine.validacion.service;

import cl.ticketcine.validacion.dto.*;
import cl.ticketcine.validacion.exception.ValidacionNotFoundException;
import cl.ticketcine.validacion.mapper.ValidacionMapper;
import cl.ticketcine.validacion.model.entity.*;
import cl.ticketcine.validacion.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidacionService {

    private final PuntoControlRepository puntoControlRepository;
    private final QrCodigoRepository qrCodigoRepository;
    private final HistorialAccesoRepository historialAccesoRepository;
    private final ReservasProyeccionRepository reservasProyeccionRepository;
    private final ValidacionMapper mapper;

    public List<PuntoControlResponse> findAllPuntosControl() {
        return mapper.toPuntoControlResponseList(puntoControlRepository.findAll());
    }

    public PuntoControlResponse findPuntoControlById(String idPunto) {
        PuntoControl punto = puntoControlRepository.findById(idPunto)
                .orElseThrow(() -> new ValidacionNotFoundException("Punto de control no encontrado con id: " + idPunto));
        return mapper.toPuntoControlResponse(punto);
    }

    public List<PuntoControlResponse> searchPuntosControl(String ubicacion) {
        return mapper.toPuntoControlResponseList(puntoControlRepository.findByUbicacionContainingIgnoreCase(ubicacion));
    }

    @Transactional
    public PuntoControlResponse createPuntoControl(PuntoControlRequest request) {
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
        PuntoControl existing = puntoControlRepository.findById(idPunto)
                .orElseThrow(() -> new ValidacionNotFoundException("Punto de control no encontrado con id: " + idPunto));

        if (!idPunto.equals(request.getIdPunto())) {
            throw new IllegalArgumentException("El id de punto en la ruta y el cuerpo deben coincidir");
        }

        existing.setUbicacion(request.getUbicacion());
        return mapper.toPuntoControlResponse(puntoControlRepository.save(existing));
    }

    @Transactional
    public void deletePuntoControl(String idPunto) {
        if (!puntoControlRepository.existsById(idPunto)) {
            throw new ValidacionNotFoundException("Punto de control no encontrado con id: " + idPunto);
        }
        puntoControlRepository.deleteById(idPunto);
    }

    public List<QrCodigoResponse> findAllQrCodigos() {
        return mapper.toQrCodigoResponseList(qrCodigoRepository.findAll());
    }

    public QrCodigoResponse findQrCodigoById(Long idQr) {
        QrCodigo qr = qrCodigoRepository.findById(idQr)
                .orElseThrow(() -> new ValidacionNotFoundException("QR no encontrado con id: " + idQr));
        return mapper.toQrCodigoResponse(qr);
    }

    public List<QrCodigoResponse> findQrCodigosByReservaId(UUID reservaId) {
        return mapper.toQrCodigoResponseList(qrCodigoRepository.findByReservaId(reservaId));
    }

    @Transactional
    public QrCodigoResponse createQrCodigo(QrCodigoRequest request) {
        QrCodigo qr = QrCodigo.builder()
                .reservaId(request.getReservaId())
                .hashCode(request.getHashCode())
                .activo(request.getActivo())
                .build();
        return mapper.toQrCodigoResponse(qrCodigoRepository.save(qr));
    }

    @Transactional
    public QrCodigoResponse updateQrCodigo(Long idQr, QrCodigoRequest request) {
        QrCodigo existing = qrCodigoRepository.findById(idQr)
                .orElseThrow(() -> new ValidacionNotFoundException("QR no encontrado con id: " + idQr));

        existing.setReservaId(request.getReservaId());
        existing.setHashCode(request.getHashCode());
        existing.setActivo(request.getActivo());

        return mapper.toQrCodigoResponse(qrCodigoRepository.save(existing));
    }

    @Transactional
    public void deleteQrCodigo(Long idQr) {
        if (!qrCodigoRepository.existsById(idQr)) {
            throw new ValidacionNotFoundException("QR no encontrado con id: " + idQr);
        }
        qrCodigoRepository.deleteById(idQr);
    }

    public List<HistorialAccesoResponse> findHistorialByPunto(String idPunto) {
        return mapper.toHistorialAccesoResponseList(historialAccesoRepository.findByPuntoControl_IdPunto(idPunto));
    }

    public List<HistorialAccesoResponse> findHistorialByQr(Long idQr) {
        return mapper.toHistorialAccesoResponseList(historialAccesoRepository.findByQrCodigo_IdQr(idQr));
    }

    @Transactional
    public HistorialAccesoResponse createHistorialAcceso(HistorialAccesoRequest request) {
        QrCodigo qr = qrCodigoRepository.findById(request.getIdQr())
                .orElseThrow(() -> new ValidacionNotFoundException("QR no encontrado con id: " + request.getIdQr()));

        PuntoControl punto = puntoControlRepository.findById(request.getIdPunto())
                .orElseThrow(() -> new ValidacionNotFoundException("Punto de control no encontrado con id: " + request.getIdPunto()));

        HistorialAcceso acceso = HistorialAcceso.builder()
                .qrCodigo(qr)
                .puntoControl(punto)
                .build();

        return mapper.toHistorialAccesoResponse(historialAccesoRepository.save(acceso));
    }

    public List<ReservasProyeccionResponse> findReservasByEmail(String email) {
        return reservasProyeccionRepository.findByUserEmail(email).stream()
                .map(mapper::toReservasProyeccionResponse)
                .toList();
    }
}
