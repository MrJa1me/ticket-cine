package cl.ticketcine.busqueda.controller;

import cl.ticketcine.busqueda.dto.CategoriaRequest;
import cl.ticketcine.busqueda.dto.CategoriaResponse;
import cl.ticketcine.busqueda.dto.PeliculaRequest;
import cl.ticketcine.busqueda.dto.PeliculaResponse;
import cl.ticketcine.busqueda.dto.UsuarioProyeccionResponse;
import cl.ticketcine.busqueda.service.BusquedaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/busqueda")
@RequiredArgsConstructor
public class BusquedaController {

    private final BusquedaService busquedaService;

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaResponse>> findAllCategorias() {
        return ResponseEntity.ok(busquedaService.findAllCategorias());
    }

    @GetMapping("/categorias/{idCat}")
    public ResponseEntity<CategoriaResponse> findCategoriaById(@PathVariable Integer idCat) {
        return ResponseEntity.ok(busquedaService.findCategoriaById(idCat));
    }

    @GetMapping("/categorias/buscar")
    public ResponseEntity<List<CategoriaResponse>> searchCategorias(@RequestParam String nombre) {
        return ResponseEntity.ok(busquedaService.searchCategorias(nombre));
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaResponse> createCategoria(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse created = busquedaService.createCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/peliculas")
    public ResponseEntity<List<PeliculaResponse>> findAllPeliculas() {
        return ResponseEntity.ok(busquedaService.findAllPeliculas());
    }

    @GetMapping("/peliculas/{slug}")
    public ResponseEntity<PeliculaResponse> findPeliculaBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(busquedaService.findPeliculaBySlug(slug));
    }

    @GetMapping("/peliculas/buscar")
    public ResponseEntity<List<PeliculaResponse>> searchPeliculasByTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(busquedaService.searchPeliculasByTitulo(titulo));
    }

    @GetMapping("/peliculas/categoria/{idCat}")
    public ResponseEntity<List<PeliculaResponse>> findPeliculasByCategoria(@PathVariable Integer idCat) {
        return ResponseEntity.ok(busquedaService.findPeliculasByCategoria(idCat));
    }

    @GetMapping("/peliculas/estreno/{anio}")
    public ResponseEntity<List<PeliculaResponse>> findPeliculasByEstrenoAnio(@PathVariable Integer anio) {
        return ResponseEntity.ok(busquedaService.findPeliculasByEstrenoAnio(anio));
    }

    @PostMapping("/peliculas")
    public ResponseEntity<PeliculaResponse> createPelicula(@Valid @RequestBody PeliculaRequest request) {
        PeliculaResponse created = busquedaService.createPelicula(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/peliculas/{slug}")
    public ResponseEntity<PeliculaResponse> updatePelicula(
            @PathVariable String slug,
            @Valid @RequestBody PeliculaRequest request) {
        return ResponseEntity.ok(busquedaService.updatePelicula(slug, request));
    }

    @DeleteMapping("/peliculas/{slug}")
    public ResponseEntity<Void> deletePelicula(@PathVariable String slug) {
        busquedaService.deletePelicula(slug);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios/{email}")
    public ResponseEntity<UsuarioProyeccionResponse> findUsuarioByEmail(@PathVariable String email) {
        return ResponseEntity.ok(busquedaService.findUsuarioByEmail(email));
    }
}
