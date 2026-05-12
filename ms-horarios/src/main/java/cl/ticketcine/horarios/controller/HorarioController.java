package cl.ticketcine.horarios.controller;

import cl.ticketcine.horarios.dto.HorarioRequest;
import cl.ticketcine.horarios.dto.HorarioResponse;
import cl.ticketcine.horarios.service.HorarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioResponse>> findAll() {
        return ResponseEntity.ok(horarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioResponse> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(horarioService.findById(id));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<HorarioResponse>> findByFecha(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(horarioService.findByFecha(fecha));
    }

    @GetMapping("/pelicula/{pelicula}")
    public ResponseEntity<List<HorarioResponse>> findByPelicula(@PathVariable String pelicula) {
        return ResponseEntity.ok(horarioService.findByPelicula(pelicula));
    }

    @PostMapping
    public ResponseEntity<HorarioResponse> create(@Valid @RequestBody HorarioRequest request) {
        HorarioResponse creado = horarioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponse> update(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody HorarioRequest request) {
        return ResponseEntity.ok(horarioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        horarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/habilitar")
    public ResponseEntity<HorarioResponse> habilitar(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(horarioService.habilitar(id));
    }

    @PutMapping("/{id}/deshabilitar")
    public ResponseEntity<HorarioResponse> deshabilitar(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(horarioService.deshabilitar(id));
    }
}
