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

import cl.ticketcine.usuarios.dto.PerfilRequest;
import cl.ticketcine.usuarios.dto.PerfilResponse;
import cl.ticketcine.usuarios.service.PerfilService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<PerfilResponse>> findAll() {
        return ResponseEntity.ok(perfilService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(perfilService.findById(id));
    }

    @GetMapping("/usuario/{usuarioEmail}")
    public ResponseEntity<PerfilResponse> findByUsuarioEmail(@PathVariable String usuarioEmail) {
        return ResponseEntity.ok(perfilService.findByUsuarioEmail(usuarioEmail));
    }

    @PostMapping
    public ResponseEntity<PerfilResponse> create(@Valid @RequestBody PerfilRequest request) {
        PerfilResponse creado = perfilService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponse> update(@PathVariable @NonNull Integer id, @Valid @RequestBody PerfilRequest request) {
        PerfilResponse actualizado = perfilService.update(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Integer id) {
        perfilService.delete(id);
        return ResponseEntity.noContent().build();
    }
}