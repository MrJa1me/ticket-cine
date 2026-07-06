package cl.ticketcine.usuarios.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
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

import cl.ticketcine.usuarios.dto.PerfilRequest;
import cl.ticketcine.usuarios.dto.PerfilResponse;
import cl.ticketcine.usuarios.dto.UsuarioRequest;
import cl.ticketcine.usuarios.dto.UsuarioResponse;
import cl.ticketcine.usuarios.service.UsuarioService;

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
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios y perfiles. Requiere JWT con rol Administrador u Organizador según la operación.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private UsuarioResponse addLinks(UsuarioResponse usuario) {
        Integer id = usuario.getIdUsuario();

        usuario.add(linkTo(methodOn(UsuarioController.class).findById(id)).withSelfRel());

        usuario.add(linkTo(methodOn(UsuarioController.class).update(id, null))
                .withRel("update").withTitle("PUT - Actualizar usuario"));

        usuario.add(linkTo(methodOn(UsuarioController.class).deleteById(id))
                .withRel("delete").withTitle("DELETE - Eliminar usuario"));

        usuario.add(linkTo(methodOn(UsuarioController.class).activar(id))
                .withRel("activar").withTitle("PUT - Activar cuenta"));

        usuario.add(linkTo(methodOn(UsuarioController.class).desactivar(id))
                .withRel("desactivar").withTitle("PUT - Desactivar cuenta"));

        usuario.add(linkTo(methodOn(UsuarioController.class).findAll())
                .withRel("all").withTitle("GET - Listado de usuarios"));

        return usuario;
    }

    @Operation(summary = "Listar todos los usuarios", description = "Requiere rol Administrador u Organizador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class)))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioResponse>> findAll() {
        List<UsuarioResponse> usuarios = usuarioService.findAll();
        usuarios.forEach(this::addLinks);

        CollectionModel<UsuarioResponse> collection = CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioController.class).findAll()).withSelfRel()
        );
        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioResponse> findById(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(addLinks(usuarioService.findById(id)));
    }

    @Operation(summary = "Obtener usuario por correo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponse> findByCorreo(
            @Parameter(description = "Correo electrónico del usuario", required = true, example = "admin@ticketcine.cl")
            @PathVariable String correo) {
        return ResponseEntity.ok(addLinks(usuarioService.findByCorreo(correo)));
    }

    @Operation(summary = "Crear usuario", description = "Requiere rol Administrador")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UsuarioResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del usuario a crear", required = true,
                content = @Content(schema = @Schema(implementation = UsuarioRequest.class)))
            @Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse creado = addLinks(usuarioService.create(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar usuario", description = "Requiere rol Administrador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @PutMapping("/id/{id}")
    public ResponseEntity<UsuarioResponse> update(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable @NonNull Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos del usuario", required = true,
                content = @Content(schema = @Schema(implementation = UsuarioRequest.class)))
            @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(addLinks(usuarioService.update(id, request)));
    }

    @Operation(summary = "Eliminar usuario", description = "Requiere rol Administrador")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable @NonNull Integer id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activar cuenta de usuario", description = "Requiere rol Administrador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cuenta activada",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @PutMapping("/id/{id}/activar")
    public ResponseEntity<UsuarioResponse> activar(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(addLinks(usuarioService.activar(id)));
    }

    @Operation(summary = "Desactivar cuenta de usuario", description = "Requiere rol Administrador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cuenta desactivada",
            content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @PutMapping("/id/{id}/desactivar")
    public ResponseEntity<UsuarioResponse> desactivar(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(addLinks(usuarioService.desactivar(id)));
    }

    @Operation(
        summary = "Agregar perfil a un usuario",
        description = "Requiere rol Cliente, Organizador o Administrador"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Perfil creado exitosamente",
            content = @Content(schema = @Schema(implementation = PerfilResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos", content = @Content)
    })
    @PostMapping("/perfiles/idUsuario/{idUsuario}")
    public ResponseEntity<PerfilResponse> addPerfilAUsuario(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable @NonNull Integer idUsuario,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del perfil", required = true,
                content = @Content(schema = @Schema(implementation = PerfilRequest.class)))
            @Valid @RequestBody PerfilRequest request) {
        PerfilResponse creado = usuarioService.addPerfilAUsuario(idUsuario, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}
