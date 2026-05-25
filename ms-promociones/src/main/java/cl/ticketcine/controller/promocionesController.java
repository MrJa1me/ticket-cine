package cl.ticketcine.controller;

import cl.ticketcine.dto.AplicacionPromoRequest;
import cl.ticketcine.dto.AplicacionPromoResponse;
import cl.ticketcine.dto.CampaniaRequest;
import cl.ticketcine.dto.CampaniaResponse;
import cl.ticketcine.dto.CuponRequest;
import cl.ticketcine.dto.CuponResponse;
import cl.ticketcine.dto.UsuariosProyeccionRequest;
import cl.ticketcine.dto.UsuariosProyeccionResponse;
import cl.ticketcine.service.promocionesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promociones")
public class promocionesController {

    private final promocionesService promocionesService;

    @GetMapping("/campanias")
    public ResponseEntity<List<CampaniaResponse>> findAllCampanias() {
        return ResponseEntity.ok(promocionesService.findAllCampanias());
    }

    @GetMapping("/campanias/{id}")
    public ResponseEntity<CampaniaResponse> findCampaniaById(@PathVariable Integer id) {
        return ResponseEntity.ok(promocionesService.findCampaniaById(id));
    }

    @PostMapping("/campanias")
    public ResponseEntity<CampaniaResponse> createCampania(@Valid @RequestBody CampaniaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionesService.createCampania(request));
    }

    @PutMapping("/campanias/{id}")
    public ResponseEntity<CampaniaResponse> updateCampania(@PathVariable Integer id,
                                                          @Valid @RequestBody CampaniaRequest request) {
        return ResponseEntity.ok(promocionesService.updateCampania(id, request));
    }

    @DeleteMapping("/campanias/{id}")
    public ResponseEntity<Void> deleteCampania(@PathVariable Integer id) {
        promocionesService.deleteCampania(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cupones")
    public ResponseEntity<List<CuponResponse>> findAllCupones() {
        return ResponseEntity.ok(promocionesService.findAllCupones());
    }

    @GetMapping("/cupones/{codigo}")
    public ResponseEntity<CuponResponse> findCuponByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(promocionesService.findCuponByCodigo(codigo));
    }

    @PostMapping("/cupones")
    public ResponseEntity<CuponResponse> createCupon(@Valid @RequestBody CuponRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionesService.createCupon(request));
    }

    @PutMapping("/cupones/{codigo}")
    public ResponseEntity<CuponResponse> updateCupon(@PathVariable String codigo,
                                                     @Valid @RequestBody CuponRequest request) {
        return ResponseEntity.ok(promocionesService.updateCupon(codigo, request));
    }

    @DeleteMapping("/cupones/{codigo}")
    public ResponseEntity<Void> deleteCupon(@PathVariable String codigo) {
        promocionesService.deleteCupon(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aplicaciones")
    public ResponseEntity<List<AplicacionPromoResponse>> findAllAplicaciones() {
        return ResponseEntity.ok(promocionesService.findAllAplicaciones());
    }

    @GetMapping("/aplicaciones/{id}")
    public ResponseEntity<AplicacionPromoResponse> findAplicacionById(@PathVariable Integer id) {
        return ResponseEntity.ok(promocionesService.findAplicacionById(id));
    }

    @PostMapping("/aplicaciones")
    public ResponseEntity<AplicacionPromoResponse> createAplicacion(@Valid @RequestBody AplicacionPromoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionesService.createAplicacion(request));
    }

    @PutMapping("/aplicaciones/{id}")
    public ResponseEntity<AplicacionPromoResponse> updateAplicacion(@PathVariable Integer id,
                                                                    @Valid @RequestBody AplicacionPromoRequest request) {
        return ResponseEntity.ok(promocionesService.updateAplicacion(id, request));
    }

    @DeleteMapping("/aplicaciones/{id}")
    public ResponseEntity<Void> deleteAplicacion(@PathVariable Integer id) {
        promocionesService.deleteAplicacion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios-proyeccion")
    public ResponseEntity<List<UsuariosProyeccionResponse>> findAllUsuariosProyeccion() {
        return ResponseEntity.ok(promocionesService.findAllUsuariosProyeccion());
    }

    @GetMapping("/usuarios-proyeccion/{email}")
    public ResponseEntity<UsuariosProyeccionResponse> findUsuariosProyeccionByEmail(@PathVariable String email) {
        return ResponseEntity.ok(promocionesService.findUsuariosProyeccionByEmail(email));
    }

    @PostMapping("/usuarios-proyeccion")
    public ResponseEntity<UsuariosProyeccionResponse> createUsuariosProyeccion(@Valid @RequestBody UsuariosProyeccionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionesService.createUsuariosProyeccion(request));
    }

    @PutMapping("/usuarios-proyeccion/{email}")
    public ResponseEntity<UsuariosProyeccionResponse> updateUsuariosProyeccion(@PathVariable String email,
                                                                              @Valid @RequestBody UsuariosProyeccionRequest request) {
        return ResponseEntity.ok(promocionesService.updateUsuariosProyeccion(email, request));
    }

    @DeleteMapping("/usuarios-proyeccion/{email}")
    public ResponseEntity<Void> deleteUsuariosProyeccion(@PathVariable String email) {
        promocionesService.deleteUsuariosProyeccion(email);
        return ResponseEntity.noContent().build();
    }
}
