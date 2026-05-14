package cl.ticketcine.usuarios.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.ticketcine.usuarios.dto.PerfilRequest;
import cl.ticketcine.usuarios.dto.PerfilResponse;
import cl.ticketcine.usuarios.exception.PerfilNotFoundException;
import cl.ticketcine.usuarios.mapper.PerfilMapper;
import cl.ticketcine.usuarios.model.Perfil;
import cl.ticketcine.usuarios.model.Usuario;
import cl.ticketcine.usuarios.repository.PerfilRepository;
import cl.ticketcine.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la lógica de negocio relacionada con perfiles.
 * Gestiona operaciones CRUD, validaciones de negocio y reglas de integridad.
 */
@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfilMapper perfilMapper;

    public List<PerfilResponse> findAll() {
        return perfilMapper.toResponseList(perfilRepository.findAll());
    }

    public PerfilResponse findById(Integer id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new PerfilNotFoundException(id));
        return perfilMapper.toResponse(perfil);
    }

    public PerfilResponse findByUsuarioEmail(String usuarioEmail) {
        Perfil perfil = perfilRepository.findByUsuarioEmail(usuarioEmail)
                .orElseThrow(() -> new PerfilNotFoundException(usuarioEmail));
        return perfilMapper.toResponse(perfil);
    }

    public PerfilResponse create(PerfilRequest request) {
        String email = Objects.requireNonNull(request.getUsuarioEmail(), "El email del usuario es obligatorio");
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + request.getUsuarioEmail()));

        if (perfilRepository.existsByUsuarioEmail(request.getUsuarioEmail())) {
            throw new RuntimeException("El usuario ya posee un perfil");
        }

        Perfil perfil = perfilMapper.toEntity(request);
        perfil.setUsuario(usuario);
        Perfil saved = perfilRepository.save(perfil);
        return perfilMapper.toResponse(saved);
    }

    public PerfilResponse update(Integer id, PerfilRequest request) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new PerfilNotFoundException(id));

        Usuario usuario = usuarioRepository.findById(request.getUsuarioEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + request.getUsuarioEmail()));

        perfilMapper.updateEntity(request, perfil);
        perfil.setUsuario(usuario);
        Perfil updated = perfilRepository.save(perfil);
        return perfilMapper.toResponse(updated);
    }

    public void delete(Integer id) {
        if (!perfilRepository.existsById(id)) {
            throw new PerfilNotFoundException(id);
        }
        perfilRepository.deleteById(id);
    }
}