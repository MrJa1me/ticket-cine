package cl.ticketcine.salas.controller;

import cl.ticketcine.salas.dto.SalaRequest;
import cl.ticketcine.salas.dto.SalaResponse;
import cl.ticketcine.salas.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaResponse>> findAll() {
        return ResponseEntity.ok(salaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponse> findById(@PathVariable @NonNull String id) {
        return ResponseEntity.ok(salaService.findById(id));
    }

    @GetMapping("/formato/{formato}")
    public ResponseEntity<List<SalaResponse>> findByFormato(@PathVariable String formato) {
        return ResponseEntity.ok(salaService.findByFormato(formato));
    }

    @GetMapping("/capacidad/{capacidad}")
    public ResponseEntity<List<SalaResponse>> findByCapacidadMin(@PathVariable Integer capacidad) {
        return ResponseEntity.ok(salaService.findByCapacidadMin(capacidad));
    }

    @PostMapping
    public ResponseEntity<SalaResponse> create(@Valid @RequestBody SalaRequest request) {
        SalaResponse creado = salaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponse> update(
            @PathVariable @NonNull String id,
            @Valid @RequestBody SalaRequest request) {
        return ResponseEntity.ok(salaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull String id) {
        salaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
