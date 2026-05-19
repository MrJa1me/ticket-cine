package cl.ticketcine.usuarios.controller;

import cl.ticketcine.usuarios.dto.PerfilRequest;
import cl.ticketcine.usuarios.dto.PerfilResponse;
import cl.ticketcine.usuarios.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<PerfilResponse>> findAll() {
        return ResponseEntity.ok(perfilService.findAll());
    }

    @GetMapping("/{usuarioEmail}")
    public ResponseEntity<PerfilResponse> findByUsuarioEmail(@PathVariable String usuarioEmail) {
        return ResponseEntity.ok(perfilService.findByUsuarioEmail(usuarioEmail));
    }

    @PostMapping
    public ResponseEntity<PerfilResponse> create(@Valid @RequestBody PerfilRequest request) {
        PerfilResponse creado = perfilService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{usuarioEmail}")
    public ResponseEntity<PerfilResponse> update(
            @PathVariable String usuarioEmail,
            @Valid @RequestBody PerfilRequest request) {
        return ResponseEntity.ok(perfilService.update(usuarioEmail, request));
    }

    @DeleteMapping("/{usuarioEmail}")
    public ResponseEntity<Void> deleteByUsuarioEmail(@PathVariable String usuarioEmail) {
        perfilService.deleteByUsuarioEmail(usuarioEmail);
        return ResponseEntity.noContent().build();
    }
}