package cl.ticketcine.usuarios.controller;

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

import cl.ticketcine.usuarios.dto.MembresiaRequest;
import cl.ticketcine.usuarios.dto.MembresiaResponse;
import cl.ticketcine.usuarios.service.MembresiaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membresias")
public class MembresiaController {

    private final MembresiaService membresiaService;

    @GetMapping
    public ResponseEntity<List<MembresiaResponse>> findAll() {
        return ResponseEntity.ok(membresiaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembresiaResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(membresiaService.findById(id));
    }

    @GetMapping("/usuario/{usuarioEmail}")
    public ResponseEntity<List<MembresiaResponse>> findByUsuarioEmail(@PathVariable String usuarioEmail) {
        return ResponseEntity.ok(membresiaService.findByUsuarioEmail(usuarioEmail));
    }

    @PostMapping
    public ResponseEntity<MembresiaResponse> create(@Valid @RequestBody MembresiaRequest request) {
        MembresiaResponse creada = membresiaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembresiaResponse> update(@PathVariable @NonNull Integer id, @Valid @RequestBody MembresiaRequest request) {
        MembresiaResponse actualizada = membresiaService.update(id, request);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Integer id) {
        membresiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}