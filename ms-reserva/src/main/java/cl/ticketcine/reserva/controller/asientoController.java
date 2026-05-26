package cl.ticketcine.reserva.controller;

import cl.ticketcine.reserva.dto.reservaRequest;
import cl.ticketcine.reserva.dto.reservaResponse;
import cl.ticketcine.reserva.service.reservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asientos")
public class asientoController {

    private final reservaService reservaService;

    @GetMapping
    public ResponseEntity<List<reservaResponse>> findAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/{idAsiento}")
    public ResponseEntity<reservaResponse> findById(@PathVariable Integer idAsiento) {
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
