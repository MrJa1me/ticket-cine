package cl.ticketcine.reserva.controller;

import cl.ticketcine.reserva.dto.SalaRequest;
import cl.ticketcine.reserva.dto.SalaResponse;
import cl.ticketcine.reserva.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/salas")
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaResponse>> findAll() {
        return ResponseEntity.ok(salaService.findAll());
    }

    @GetMapping("/{idSala}")
    public ResponseEntity<SalaResponse> findById(@PathVariable String idSala) {
        return ResponseEntity.ok(salaService.findById(idSala));
    }

    @PostMapping
    public ResponseEntity<SalaResponse> create(@Valid @RequestBody salaRequest request) {
        SalaResponse creado = salaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{idSala}")
    public ResponseEntity<SalaResponse> update(
            @PathVariable String idSala,
            @Valid @RequestBody salaRequest request) {
        return ResponseEntity.ok(salaService.update(idSala, request));
    }

    @DeleteMapping("/{idSala}")
    public ResponseEntity<Void> deleteById(@PathVariable String idSala) {
        salaService.deleteById(idSala);
        return ResponseEntity.noContent().build();
    }
}
