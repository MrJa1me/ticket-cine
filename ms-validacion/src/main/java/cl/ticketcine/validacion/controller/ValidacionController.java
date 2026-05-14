package cl.ticketcine.validacion.controller;

import cl.ticketcine.validacion.dto.*;
import cl.ticketcine.validacion.service.ValidacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/validacion")
@RequiredArgsConstructor
public class ValidacionController {

    private final ValidacionService service;

    @GetMapping("/puntos-control")
    public ResponseEntity<List<PuntoControlResponse>> findAllPuntosControl() {
        return ResponseEntity.ok(service.findAllPuntosControl());
    }

    @GetMapping("/puntos-control/{id}")
    public ResponseEntity<PuntoControlResponse> findPuntoControlById(@PathVariable String id) {
        return ResponseEntity.ok(service.findPuntoControlById(id));
    }

    @GetMapping("/puntos-control/buscar")
    public ResponseEntity<List<PuntoControlResponse>> searchPuntosControl(@RequestParam String ubicacion) {
        return ResponseEntity.ok(service.searchPuntosControl(ubicacion));
    }

    @PostMapping("/puntos-control")
    public ResponseEntity<PuntoControlResponse> createPuntoControl(@Valid @RequestBody PuntoControlRequest request) {
        PuntoControlResponse created = service.createPuntoControl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/puntos-control/{id}")
    public ResponseEntity<PuntoControlResponse> updatePuntoControl(
            @PathVariable String id,
            @Valid @RequestBody PuntoControlRequest request) {
        return ResponseEntity.ok(service.updatePuntoControl(id, request));
    }

    @DeleteMapping("/puntos-control/{id}")
    public ResponseEntity<Void> deletePuntoControl(@PathVariable String id) {
        service.deletePuntoControl(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/qr-codigos")
    public ResponseEntity<List<QrCodigoResponse>> findAllQrCodigos() {
        return ResponseEntity.ok(service.findAllQrCodigos());
    }

    @GetMapping("/qr-codigos/{id}")
    public ResponseEntity<QrCodigoResponse> findQrCodigoById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findQrCodigoById(id));
    }

    @GetMapping("/qr-codigos/reserva/{reservaId}")
    public ResponseEntity<List<QrCodigoResponse>> findQrCodigosByReservaId(@PathVariable UUID reservaId) {
        return ResponseEntity.ok(service.findQrCodigosByReservaId(reservaId));
    }

    @PostMapping("/qr-codigos")
    public ResponseEntity<QrCodigoResponse> createQrCodigo(@Valid @RequestBody QrCodigoRequest request) {
        QrCodigoResponse created = service.createQrCodigo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/qr-codigos/{id}")
    public ResponseEntity<QrCodigoResponse> updateQrCodigo(
            @PathVariable Long id,
            @Valid @RequestBody QrCodigoRequest request) {
        return ResponseEntity.ok(service.updateQrCodigo(id, request));
    }

    @DeleteMapping("/qr-codigos/{id}")
    public ResponseEntity<Void> deleteQrCodigo(@PathVariable Long id) {
        service.deleteQrCodigo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/historial-accesos/punto/{idPunto}")
    public ResponseEntity<List<HistorialAccesoResponse>> findHistorialByPunto(@PathVariable String idPunto) {
        return ResponseEntity.ok(service.findHistorialByPunto(idPunto));
    }

    @GetMapping("/historial-accesos/qr/{idQr}")
    public ResponseEntity<List<HistorialAccesoResponse>> findHistorialByQr(@PathVariable Long idQr) {
        return ResponseEntity.ok(service.findHistorialByQr(idQr));
    }

    @PostMapping("/historial-accesos")
    public ResponseEntity<HistorialAccesoResponse> createHistorialAcceso(@Valid @RequestBody HistorialAccesoRequest request) {
        HistorialAccesoResponse created = service.createHistorialAcceso(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/reservas-proyeccion")
    public ResponseEntity<List<ReservasProyeccionResponse>> findReservasByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.findReservasByEmail(email));
    }
}
