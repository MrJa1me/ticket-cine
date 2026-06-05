package cl.ticketcine.promociones.controller;

import cl.ticketcine.promociones.dto.CuponRequest;
import cl.ticketcine.promociones.dto.CuponResponse;
import cl.ticketcine.promociones.service.PromocionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cupones")
public class CuponController {

    private final PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<CuponResponse>> findAll() {
        return ResponseEntity.ok(promocionService.findAllCupones());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CuponResponse> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(promocionService.findCuponByCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<CuponResponse> create(@Valid @RequestBody CuponRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.createCupon(request));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<CuponResponse> update(@PathVariable String codigo,
                                                 @Valid @RequestBody CuponRequest request) {
        return ResponseEntity.ok(promocionService.updateCupon(codigo, request));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> delete(@PathVariable String codigo) {
        promocionService.deleteCupon(codigo);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{codigo}/desactivar")
    public ResponseEntity<CuponResponse> deactivate(@PathVariable String codigo) {
        return ResponseEntity.ok(promocionService.deactivateCupon(codigo));
    }
}
