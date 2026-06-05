package cl.ticketcine.promociones.controller;

import cl.ticketcine.promociones.dto.AplicacionPromoRequest;
import cl.ticketcine.promociones.dto.AplicacionPromoResponse;
import cl.ticketcine.promociones.service.PromocionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aplicaciones-promociones")
public class AplicacionPromoController {

    private final PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<AplicacionPromoResponse>> findAll() {
        return ResponseEntity.ok(promocionService.findAllAplicaciones());
    }

    @GetMapping("/{idApp}")
    public ResponseEntity<AplicacionPromoResponse> findById(@PathVariable Long idApp) {
        return ResponseEntity.ok(promocionService.findAplicacionById(idApp));
    }

    @PostMapping
    public ResponseEntity<AplicacionPromoResponse> apply(@Valid @RequestBody AplicacionPromoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.applyCupon(request));
    }
}
