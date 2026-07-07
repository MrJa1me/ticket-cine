package cl.ticketcine.catalogo.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

import cl.ticketcine.catalogo.dto.PeliculaRequestDTO;
import cl.ticketcine.catalogo.dto.PeliculaResponseDTO;
import cl.ticketcine.catalogo.service.PeliculaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/Peliculas")
@Tag(name = "Peliculas", description = "API para la gestion del catalogo de Peliculas")
@RequiredArgsConstructor
public class PeliculaController {

    private final PeliculaService service;

    /**
     * Agrega links de navegacion HATEOAS al Pelicula del catalogo:
     * operaciones CRUD.
     */
    private PeliculaResponseDTO addLinks(PeliculaResponseDTO pelicula) {
        Integer id = pelicula.getIdPelicula();

        pelicula.add(linkTo(methodOn(PeliculaController.class).obtener(id)).withSelfRel());

        pelicula.add(linkTo(methodOn(PeliculaController.class).actualizar(id, null))
                .withRel("update").withTitle("PUT - Actualizar Pelicula"));

        pelicula.add(linkTo(methodOn(PeliculaController.class).eliminar(id))
                .withRel("delete").withTitle("DELETE - Eliminar Pelicula"));

        pelicula.add(linkTo(methodOn(PeliculaController.class).listar())
                .withRel("all").withTitle("GET - Listado de Peliculas"));

        return pelicula;
    }

    @Operation(summary = "Obtener todos los Peliculas", description = "Retorna la lista completa de Peliculas del catalogo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PeliculaResponseDTO.class)))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para consultar Peliculas", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<PeliculaResponseDTO>> listar() {
        List<PeliculaResponseDTO> peliculas = service.obtenerTodos();
        peliculas.forEach(this::addLinks);

        CollectionModel<PeliculaResponseDTO> collection = CollectionModel.of(
                peliculas,
                linkTo(methodOn(PeliculaController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Obtener Pelicula por ID", description = "Retorna un Pelicula seg\u00fan su identificador \u00fanico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pelicula encontrada",
            content = @Content(schema = @Schema(implementation = PeliculaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para consultar Peliculas", content = @Content)
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<PeliculaResponseDTO> obtener(
            @Parameter(description = "ID del Pelicula", required = true, example = "1")
            @PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(addLinks(service.buscarPorId(id)));
    }

    @Operation(summary = "Crear un nuevo Pelicula", description = "Registra un nuevo Pelicula en el catalogo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pelicula creado exitosamente",
            content = @Content(schema = @Schema(implementation = PeliculaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para crear Peliculas", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PeliculaResponseDTO> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del Pelicula a crear", required = true,
                content = @Content(schema = @Schema(implementation = PeliculaRequestDTO.class)))
            @Valid @RequestBody PeliculaRequestDTO request) {
        PeliculaResponseDTO creado = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un Pelicula", description = "Actualiza los datos de un Pelicula existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pelicula actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = PeliculaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para actualizar Peliculas", content = @Content)
    })
    @PutMapping("/id/{id}")
    public ResponseEntity<PeliculaResponseDTO> actualizar(
            @Parameter(description = "ID del Pelicula a actualizar", required = true, example = "1")
            @PathVariable @NonNull Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos del Pelicula", required = true,
                content = @Content(schema = @Schema(implementation = PeliculaRequestDTO.class)))
            @Valid @RequestBody PeliculaRequestDTO request) {
        return ResponseEntity.ok(addLinks(service.actualizar(id, request)));
    }

    @Operation(summary = "Eliminar un Pelicula", description = "Elimina un Pelicula del catalogo por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pelicula eliminado exitosamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para eliminar Peliculas", content = @Content)
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del Pelicula a eliminar", required = true, example = "1")
            @PathVariable @NonNull Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Verificar existencia por ID de Pelicula",
        description = "Comprueba si ya existe un Pelicula registrado con el ID indicado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Verificacion realizada exitosamente",
            content = @Content(schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos para consultar Peliculas", content = @Content)
    })
    @GetMapping("/existe/idPelicula/{idPelicula}")
    public ResponseEntity<Boolean> existePorId(
            @Parameter(description = "ID del Pelicula a verificar", required = true, example = "1")
            @PathVariable Integer idPelicula) {
        // Endpoint utilitario (devuelve Boolean): no aplica HATEOAS
        return ResponseEntity.ok(service.existePorId(idPelicula));
    }
}
