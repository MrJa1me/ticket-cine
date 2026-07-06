package cl.ticketcine.boletos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ticketcine.boletos.dto.ProyPeliculaResponse;
import cl.ticketcine.boletos.service.PeliculaProyeccionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/proyPeliculas")
@RequiredArgsConstructor
public class ProyPeliculaController {

    private final PeliculaProyeccionService PeliculaProyeccionService;

    @GetMapping
    public ResponseEntity<List<ProyPeliculaResponse>> findAll() {
        return ResponseEntity.ok(PeliculaProyeccionService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProyPeliculaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(PeliculaProyeccionService.findById(id));
    }
}
