package cl.ticketcine.reserva.controller;

import cl.ticketcine.reserva.dto.ReservaRequest;
import cl.ticketcine.reserva.dto.ReservaResponse;
import cl.ticketcine.reserva.dto.reservaRequest;
import cl.ticketcine.reserva.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asientos")
public class AsientoController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> findAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/{idAsiento}")
    public ResponseEntity<ReservaResponse> findById(@PathVariable Integer idAsiento) {
        return ResponseEntity.ok(reservaService.findById(idAsiento));
    }

    @GetMapping("/sala/{idSala}")
    public ResponseEntity<List<ReservaResponse>> findBySalaId(@PathVariable String idSala) {
        return ResponseEntity.ok(reservaService.findBySalaId(idSala));
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> create(@Valid @RequestBody reservaRequest request) {
        ReservaResponse creado = reservaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{idAsiento}")
    public ResponseEntity<ReservaResponse> update(
            @PathVariable Integer idAsiento,
            @Valid @RequestBody reservaRequest request) {
        return ResponseEntity.ok(reservaService.update(idAsiento, request));
    }

    @DeleteMapping("/{idAsiento}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer idAsiento) {
        reservaService.deleteById(idAsiento);
        return ResponseEntity.noContent().build();
    }
}
