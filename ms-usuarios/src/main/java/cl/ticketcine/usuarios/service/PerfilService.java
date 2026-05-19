package cl.ticketcine.usuarios.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.ticketcine.usuarios.dto.PerfilRequest;
import cl.ticketcine.usuarios.dto.PerfilResponse;
import cl.ticketcine.usuarios.exception.PerfilNotFoundException;
import cl.ticketcine.usuarios.mapper.PerfilMapper;
import cl.ticketcine.usuarios.model.entity.Perfil;
import cl.ticketcine.usuarios.model.entity.Usuario;
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
        String emailNotNull = Objects.requireNonNull(usuarioEmail, "El email del usuario es obligatorio");
        Perfil perfil = perfilRepository.findByUsuarioEmail(emailNotNull)
                .orElseThrow(() -> new PerfilNotFoundException(emailNotNull));
        return perfilMapper.toResponse(perfil);
    }

    public PerfilResponse create(PerfilRequest request) {
        Objects.requireNonNull(request, "La solicitud de perfil es obligatoria");
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

    public PerfilResponse update(String usuarioEmail, PerfilRequest request) {
        Objects.requireNonNull(request, "La solicitud de perfil es obligatoria");
        String emailToUpdate = Objects.requireNonNull(usuarioEmail, "El email del usuario es obligatorio");
        if (!emailToUpdate.equals(request.getUsuarioEmail())) {
            throw new IllegalArgumentException("El email de la ruta y el body deben coincidir");
        }

        Perfil perfil = perfilRepository.findByUsuarioEmail(emailToUpdate)
                .orElseThrow(() -> new PerfilNotFoundException(emailToUpdate));

        Usuario usuario = usuarioRepository.findById(emailToUpdate)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + emailToUpdate));

        perfilMapper.updateEntity(request, perfil);
        perfil.setUsuario(usuario);
        Perfil updated = perfilRepository.save(perfil);
        return perfilMapper.toResponse(updated);
    }

    public void deleteByUsuarioEmail(String usuarioEmail) {
        String emailNotNull = Objects.requireNonNull(usuarioEmail, "El email del usuario es obligatorio");
        Perfil perfil = perfilRepository.findByUsuarioEmail(emailNotNull)
                .orElseThrow(() -> new PerfilNotFoundException(emailNotNull));
        perfilRepository.delete(perfil);
    }
}