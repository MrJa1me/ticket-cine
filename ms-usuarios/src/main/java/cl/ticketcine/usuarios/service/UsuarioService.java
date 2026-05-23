package cl.ticketcine.usuarios.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.ticketcine.usuarios.event.UsuarioEventProducer;

import cl.ticketcine.usuarios.dto.UsuarioRequest;
import cl.ticketcine.usuarios.dto.UsuarioResponse;
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
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioEventProducer usuarioEventProducer;

    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse findById(String email) {
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new UsuarioNotFoundException(email));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse findByEmail(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        Usuario usuario = usuarioRepository.findByEmail(emailNotNull)
                .orElseThrow(() -> new UsuarioNotFoundException(emailNotNull));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse create(UsuarioRequest request) {
        Objects.requireNonNull(request, "La solicitud de usuario es obligatoria");
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplicadoException(request.getEmail());
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        Usuario saved = usuarioRepository.save(usuario);
        usuarioEventProducer.publishUsuarioCreated(saved.getEmail(), saved.getNombre());
        return usuarioMapper.toResponse(saved);
    }

    public UsuarioResponse update(String email, UsuarioRequest request) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        Objects.requireNonNull(request, "La solicitud de usuario es obligatoria");
        Usuario usuario = usuarioRepository.findById(emailNotNull)
                .orElseThrow(() -> new UsuarioNotFoundException(emailNotNull));

        usuarioMapper.updateEntity(request, usuario);
        Usuario updated = usuarioRepository.save(usuario);
        usuarioEventProducer.publishUsuarioUpdated(updated.getEmail(), updated.getNombre());
        return usuarioMapper.toResponse(updated);
    }

    public void delete(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        if (!usuarioRepository.existsById(emailNotNull)) {
            throw new UsuarioNotFoundException(emailNotNull);
        }
        usuarioRepository.deleteById(emailNotNull);
        usuarioEventProducer.publishUsuarioDeleted(emailNotNull);
    }

    public boolean existsByEmail(String email) {
        String emailNotNull = Objects.requireNonNull(email, "El email es obligatorio");
        return usuarioRepository.existsByEmail(emailNotNull);
    }

    public void deleteByEmail(String email) {
        delete(email);
    }
}