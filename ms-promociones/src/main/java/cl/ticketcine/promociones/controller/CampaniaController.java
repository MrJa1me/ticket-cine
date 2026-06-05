package cl.ticketcine.promociones.controller;

import cl.ticketcine.promociones.dto.CampaniaRequest;
import cl.ticketcine.promociones.dto.CampaniaResponse;
import cl.ticketcine.promociones.service.PromocionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/campanias")
public class CampaniaController {

    private final PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<CampaniaResponse>> findAll() {
        return ResponseEntity.ok(promocionService.findAllCampanias());
    }

    @GetMapping("/{idCamp}")
    public ResponseEntity<CampaniaResponse> findById(@PathVariable Long idCamp) {
        return ResponseEntity.ok(promocionService.findCampaniaById(idCamp));
    }

    @PostMapping
    public ResponseEntity<CampaniaResponse> create(@Valid @RequestBody CampaniaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.createCampania(request));
    }

    @PutMapping("/{idCamp}")
    public ResponseEntity<CampaniaResponse> update(@PathVariable Long idCamp,
                                                   @Valid @RequestBody CampaniaRequest request) {
        return ResponseEntity.ok(promocionService.updateCampania(idCamp, request));
    }

    @DeleteMapping("/{idCamp}")
    public ResponseEntity<Void> delete(@PathVariable Long idCamp) {
        promocionService.deleteCampania(idCamp);
        return ResponseEntity.noContent().build();
    }
}
