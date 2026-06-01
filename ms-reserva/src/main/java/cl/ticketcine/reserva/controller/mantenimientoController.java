package cl.ticketcine.reserva.controller;

import cl.ticketcine.reserva.dto.*;
import cl.ticketcine.reserva.service.MantenimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mantenimientos")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    @GetMapping
    public ResponseEntity<List<MantenimientoResponse>> findAll() {
        return ResponseEntity.ok(mantenimientoService.findAll());
    }

    @GetMapping("/{idMaint}")
    public ResponseEntity<MantenimientoResponse> findById(@PathVariable Integer idMaint) {
        return ResponseEntity.ok(mantenimientoService.findById(idMaint));
    }

    @GetMapping("/sala/{idSala}")
    public ResponseEntity<List<MantenimientoResponse>> findBySalaId(@PathVariable String idSala) {
        return ResponseEntity.ok(mantenimientoService.findBySalaId(idSala));
    }

    @PostMapping
    public ResponseEntity<MantenimientoResponse> create(@Valid @RequestBody MantenimientoRequest request) {
        MantenimientoResponse creado = mantenimientoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{idMaint}")
    public ResponseEntity<MantenimientoResponse> update(
            @PathVariable Integer idMaint,
            @Valid @RequestBody MantenimientoRequest request) {
        return ResponseEntity.ok(mantenimientoService.update(idMaint, request));
    }

    @DeleteMapping("/{idMaint}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer idMaint) {
        mantenimientoService.deleteById(idMaint);
        return ResponseEntity.noContent().build();
    }
}
