package cl.ticketcine.usuarios.service;

import java.util.List;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.ticketcine.common.event.UsuarioCreatedEvent;
import cl.ticketcine.common.event.UsuarioDeletedEvent;
import cl.ticketcine.common.event.UsuarioUpdatedEvent;
import cl.ticketcine.usuarios.dto.UsuarioRequest;
import cl.ticketcine.usuarios.dto.UsuarioResponse;
import cl.ticketcine.usuarios.event.UsuarioEventProducer;
import cl.ticketcine.usuarios.exception.EmailDuplicadoException;
import cl.ticketcine.usuarios.exception.UsuarioNotFoundException;
import cl.ticketcine.usuarios.mapper.UsuarioMapper;
import cl.ticketcine.usuarios.model.entity.Usuario;
import cl.ticketcine.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la lógica de negocio relacionada con usuarios.
 * Gestiona operaciones CRUD, validaciones de negocio y reglas de integridad.
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioEventProducer usuarioEventProducer;

    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse findByEmail(String email) {
        return usuarioMapper.toResponse(getUsuarioByEmail(email));
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        Objects.requireNonNull(request, "La solicitud de usuario es obligatoria");
        validateEmailUnico(request.getEmail());
        Usuario usuario = usuarioMapper.toEntity(request);
        usuarioRepository.save(usuario);
        UsuarioCreatedEvent event = UsuarioCreatedEvent.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .build();
        usuarioEventProducer.sendCreated(event);
        return usuarioMapper.toResponse(usuario);

    }
    @Transactional
    public void save(String email, String nombre) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public UsuarioResponse update(String email, UsuarioRequest request) {
        Objects.requireNonNull(request, "La solicitud de usuario es obligatoria");
        String emailToUpdate = Objects.requireNonNull(email, "El email del usuario es obligatorio");
        if (!emailToUpdate.equals(request.getEmail())) {
            throw new IllegalArgumentException("El email de la ruta y el body deben coincidir");
        }
        if (!checkMismoEmail(emailToUpdate, request.getEmail())) {
            validateEmailUnico(request.getEmail());
        }
        Usuario usuario = getUsuarioByEmail(emailToUpdate);
        usuarioMapper.updateEntity(request, usuario);
        usuarioRepository.save(usuario);
        UsuarioUpdatedEvent event = UsuarioUpdatedEvent.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .build();
        usuarioEventProducer.sendUpdated(event);
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public void deleteByEmail(String email) {
        Usuario usuario = getUsuarioByEmail(email);
        usuarioRepository.delete(usuario);
        UsuarioDeletedEvent event = new UsuarioDeletedEvent(usuario.getEmail());
        usuarioEventProducer.sendDeleted(event);
    }

    private void validateEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new EmailDuplicadoException(email);
        }
    }

    @NonNull
    private Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException(email));
    }

    private boolean checkMismoEmail(String emailRuta, String emailBody) {
        return emailRuta.equalsIgnoreCase(emailBody);
    }
}
