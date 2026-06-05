package cl.ticketcine.promociones.controller;

import cl.ticketcine.promociones.dto.UsuarioProyeccionRequest;
import cl.ticketcine.promociones.dto.UsuarioProyeccionResponse;
import cl.ticketcine.promociones.service.PromocionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios-proyeccion")
public class UsuarioProyeccionController {

    private final PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<UsuarioProyeccionResponse>> findAll() {
        return ResponseEntity.ok(promocionService.findAllUsuariosProyeccion());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioProyeccionResponse> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(promocionService.findUsuarioProyeccionByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UsuarioProyeccionResponse> create(@Valid @RequestBody UsuarioProyeccionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.createUsuarioProyeccion(request));
    }

    @PutMapping("/{email}")
    public ResponseEntity<UsuarioProyeccionResponse> update(@PathVariable String email,
                                                            @Valid @RequestBody UsuarioProyeccionRequest request) {
        return ResponseEntity.ok(promocionService.updateUsuarioProyeccion(email, request));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        promocionService.deleteUsuarioProyeccion(email);
        return ResponseEntity.noContent().build();
    }
}
