package cl.ticketcine.notificaciones.controller;

import cl.ticketcine.notificaciones.dto.ColaEnvioRequest;
import cl.ticketcine.notificaciones.dto.ColaEnvioResponse;
import cl.ticketcine.notificaciones.dto.LogErrorRequest;
import cl.ticketcine.notificaciones.dto.LogErrorResponse;
import cl.ticketcine.notificaciones.dto.PlantillaRequest;
import cl.ticketcine.notificaciones.dto.PlantillaResponse;
import cl.ticketcine.notificaciones.dto.UsuariosProyeccionRequest;
import cl.ticketcine.notificaciones.dto.UsuariosProyeccionResponse;
import cl.ticketcine.notificaciones.service.NotificacionesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notificaciones")
public class notificacionesController {

    private final NotificacionesService notificacionesService;

    @GetMapping("/plantillas")
    public ResponseEntity<List<PlantillaResponse>> findAllPlantillas() {
        return ResponseEntity.ok(notificacionesService.findAllPlantillas());
    }

    @GetMapping("/plantillas/{id}")
    public ResponseEntity<PlantillaResponse> findPlantillaById(@PathVariable String id) {
        return ResponseEntity.ok(notificacionesService.findPlantillaById(id));
    }

    @PostMapping("/plantillas")
    public ResponseEntity<PlantillaResponse> createPlantilla(@Valid @RequestBody PlantillaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionesService.createPlantilla(request));
    }

    @PutMapping("/plantillas/{id}")
    public ResponseEntity<PlantillaResponse> updatePlantilla(@PathVariable String id,
                                                            @Valid @RequestBody PlantillaRequest request) {
        return ResponseEntity.ok(notificacionesService.updatePlantilla(id, request));
    }

    @DeleteMapping("/plantillas/{id}")
    public ResponseEntity<Void> deletePlantilla(@PathVariable String id) {
        notificacionesService.deletePlantilla(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cola")
    public ResponseEntity<List<ColaEnvioResponse>> findAllColaEnvios() {
        return ResponseEntity.ok(notificacionesService.findAllColaEnvios());
    }

    @GetMapping("/cola/{id}")
    public ResponseEntity<ColaEnvioResponse> findColaEnvioById(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionesService.findColaEnvioById(id));
    }

    @PostMapping("/cola")
    public ResponseEntity<ColaEnvioResponse> createColaEnvio(@Valid @RequestBody ColaEnvioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionesService.createColaEnvio(request));
    }

    @PutMapping("/cola/{id}")
    public ResponseEntity<ColaEnvioResponse> updateColaEnvio(@PathVariable Long id,
                                                            @Valid @RequestBody ColaEnvioRequest request) {
        return ResponseEntity.ok(notificacionesService.updateColaEnvio(id, request));
    }

    @DeleteMapping("/cola/{id}")
    public ResponseEntity<Void> deleteColaEnvio(@PathVariable Long id) {
        notificacionesService.deleteColaEnvio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios-proyeccion")
    public ResponseEntity<List<UsuariosProyeccionResponse>> findAllUsuariosProyeccion() {
        return ResponseEntity.ok(notificacionesService.findAllUsuariosProyeccion());
    }

    @GetMapping("/usuarios-proyeccion/{email}")
    public ResponseEntity<UsuariosProyeccionResponse> findUsuariosProyeccionByEmail(@PathVariable String email) {
        return ResponseEntity.ok(notificacionesService.findUsuariosProyeccionByEmail(email));
    }

    @PostMapping("/usuarios-proyeccion")
    public ResponseEntity<UsuariosProyeccionResponse> createUsuariosProyeccion(@Valid @RequestBody UsuariosProyeccionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionesService.createUsuariosProyeccion(request));
    }

    @PutMapping("/usuarios-proyeccion/{email}")
    public ResponseEntity<UsuariosProyeccionResponse> updateUsuariosProyeccion(@PathVariable String email,
                                                                              @Valid @RequestBody UsuariosProyeccionRequest request) {
        return ResponseEntity.ok(notificacionesService.updateUsuariosProyeccion(email, request));
    }

    @DeleteMapping("/usuarios-proyeccion/{email}")
    public ResponseEntity<Void> deleteUsuariosProyeccion(@PathVariable String email) {
        notificacionesService.deleteUsuariosProyeccion(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logs")
    public ResponseEntity<List<LogErrorResponse>> findAllLogs() {
        return ResponseEntity.ok(notificacionesService.findAllLogs());
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<LogErrorResponse> findLogById(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionesService.findLogById(id));
    }

    @PostMapping("/logs")
    public ResponseEntity<LogErrorResponse> createLog(@Valid @RequestBody LogErrorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionesService.createLog(request));
    }

    @PutMapping("/logs/{id}")
    public ResponseEntity<LogErrorResponse> updateLog(@PathVariable Long id,
                                                     @Valid @RequestBody LogErrorRequest request) {
        return ResponseEntity.ok(notificacionesService.updateLog(id, request));
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        notificacionesService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}
