package cl.ticketcine.reserva.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ticketcine.reserva.dto.reservaRequest;
import cl.ticketcine.reserva.dto.reservaResponse;
import cl.ticketcine.reserva.service.reservaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservas")
public class reservaController {

    private final reservaService reservaService;

    @GetMapping
    public ResponseEntity<List<reservaResponse>> findAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/{idAsiento}")
    public ResponseEntity<reservaResponse> findById(@PathVariable @NonNull Integer idAsiento) {
        return ResponseEntity.ok(reservaService.findById(idAsiento));
    }

    @GetMapping("/sala/{idSala}")
    public ResponseEntity<List<reservaResponse>> findBySalaId(@PathVariable String idSala) {
        return ResponseEntity.ok(reservaService.findBySalaId(idSala));
    }

    @PostMapping
    public ResponseEntity<reservaResponse> create(@Valid @RequestBody reservaRequest request) {
        reservaResponse creado = reservaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{idAsiento}")
    public ResponseEntity<reservaResponse> update(
            @PathVariable @NonNull Integer idAsiento,
            @Valid @RequestBody reservaRequest request) {
        return ResponseEntity.ok(reservaService.update(idAsiento, request));
    }

    @DeleteMapping("/{idAsiento}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer idAsiento) {
        reservaService.deleteById(idAsiento);
        return ResponseEntity.noContent().build();
    }
}
