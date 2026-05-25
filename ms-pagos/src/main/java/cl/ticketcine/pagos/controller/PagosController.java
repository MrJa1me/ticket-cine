package cl.ticketcine.pagos.controller;

import cl.ticketcine.pagos.dto.PagosRequest;
import cl.ticketcine.pagos.dto.PagosResponse;
import cl.ticketcine.pagos.service.PagosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pagos")
public class PagosController {

    private final PagosService service;

    @GetMapping
    public ResponseEntity<List<PagosResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagosResponse> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PagosResponse> create(@Valid @RequestBody PagosRequest request) {
        PagosResponse creado = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagosResponse> update(@PathVariable @NonNull Long id, @Valid @RequestBody PagosRequest request) {
        PagosResponse actualizado = service.update(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
