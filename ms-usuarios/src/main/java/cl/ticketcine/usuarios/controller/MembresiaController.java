package cl.ticketcine.usuarios.controller;

import cl.ticketcine.usuarios.dto.MembresiaRequest;
import cl.ticketcine.usuarios.dto.MembresiaResponse;
import cl.ticketcine.usuarios.service.MembresiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membresias")
public class MembresiaController {

    private final MembresiaService membresiaService;

    @GetMapping
    public ResponseEntity<List<MembresiaResponse>> findAll() {
        return ResponseEntity.ok(membresiaService.findAll());
    }

    @GetMapping("/{usuarioEmail}")
    public ResponseEntity<MembresiaResponse> findByUsuarioEmail(@PathVariable String usuarioEmail) {
        return ResponseEntity.ok(membresiaService.findByUsuarioEmail(usuarioEmail));
    }

    @PostMapping
    public ResponseEntity<MembresiaResponse> create(@Valid @RequestBody MembresiaRequest request) {
        MembresiaResponse creado = membresiaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{usuarioEmail}")
    public ResponseEntity<MembresiaResponse> update(
            @PathVariable String usuarioEmail,
            @Valid @RequestBody MembresiaRequest request) {
        return ResponseEntity.ok(membresiaService.update(usuarioEmail, request));
    }

    @DeleteMapping("/{usuarioEmail}")
    public ResponseEntity<Void> deleteByUsuarioEmail(@PathVariable String usuarioEmail) {
        membresiaService.deleteByUsuarioEmail(usuarioEmail);
        return ResponseEntity.noContent().build();
    }
}