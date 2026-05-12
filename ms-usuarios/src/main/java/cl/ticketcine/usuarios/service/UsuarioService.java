package cl.ticketcine.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.ticketcine.usuarios.dto.UsuarioRequest;
import cl.ticketcine.usuarios.dto.UsuarioResponse;
import cl.ticketcine.usuarios.exception.EmailDuplicadoException;
import cl.ticketcine.usuarios.exception.UsuarioNotFoundException;
import cl.ticketcine.usuarios.mapper.UsuarioMapper;
import cl.ticketcine.usuarios.model.Usuario;
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

    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse findById(String email) {
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new UsuarioNotFoundException(email));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException(email));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse create(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplicadoException(request.getEmail());
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(saved);
    }

    public UsuarioResponse update(String email, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new UsuarioNotFoundException(email));

        usuarioMapper.updateEntity(request, usuario);
        Usuario updated = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(updated);
    }

    public void delete(String email) {
        if (!usuarioRepository.existsById(email)) {
            throw new UsuarioNotFoundException(email);
        }
        usuarioRepository.deleteById(email);
    }
}