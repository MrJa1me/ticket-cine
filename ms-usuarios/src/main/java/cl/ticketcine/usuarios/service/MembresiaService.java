package cl.ticketcine.usuarios.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.ticketcine.usuarios.dto.MembresiaRequest;
import cl.ticketcine.usuarios.dto.MembresiaResponse;
import cl.ticketcine.usuarios.exception.MembresiaNotFoundException;
import cl.ticketcine.usuarios.mapper.MembresiaMapper;
import cl.ticketcine.usuarios.model.entity.Membresia;
import cl.ticketcine.usuarios.model.entity.Usuario;
import cl.ticketcine.usuarios.repository.MembresiaRepository;
import cl.ticketcine.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la lógica de negocio relacionada con membresias.
 * Gestiona operaciones CRUD, validaciones de negocio y reglas de integridad.
 */
@Service
@RequiredArgsConstructor
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MembresiaMapper membresiaMapper;

    public List<MembresiaResponse> findAll() {
        return membresiaMapper.toResponseList(membresiaRepository.findAll());
    }

    public MembresiaResponse findById(Integer id) {
        int idToFind = Objects.requireNonNull(id, "El ID de la membresía es obligatorio");
        Membresia membresia = membresiaRepository.findById(idToFind)
                .orElseThrow(() -> new MembresiaNotFoundException(idToFind));
        return membresiaMapper.toResponse(membresia);
    }

    public MembresiaResponse findByUsuarioEmail(String usuarioEmail) {
        String emailNotNull = Objects.requireNonNull(usuarioEmail, "El email del usuario es obligatorio");
        Membresia membresia = membresiaRepository.findByUsuarioEmail(emailNotNull)
                .orElseThrow(() -> new MembresiaNotFoundException(emailNotNull, "usuario"));
        return membresiaMapper.toResponse(membresia);
    }

    public MembresiaResponse create(MembresiaRequest request) {
        Objects.requireNonNull(request, "La solicitud de membresía es obligatoria");
        String email = Objects.requireNonNull(request.getUsuarioEmail(), "El email del usuario es obligatorio");
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + request.getUsuarioEmail()));

        Membresia membresia = membresiaMapper.toEntity(request);
        membresia.setUsuario(usuario);
        Membresia saved = membresiaRepository.save(membresia);
        return membresiaMapper.toResponse(saved);
    }

    public MembresiaResponse update(String usuarioEmail, MembresiaRequest request) {
        Objects.requireNonNull(request, "La solicitud de membresía es obligatoria");
        String emailToUpdate = Objects.requireNonNull(usuarioEmail, "El email del usuario es obligatorio");
        if (!emailToUpdate.equals(request.getUsuarioEmail())) {
            throw new IllegalArgumentException("El email de la ruta y el body deben coincidir");
        }

        Membresia membresia = membresiaRepository.findByUsuarioEmail(emailToUpdate)
                .orElseThrow(() -> new MembresiaNotFoundException(emailToUpdate, "usuario"));

        Usuario usuario = usuarioRepository.findById(emailToUpdate)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + emailToUpdate));

        membresiaMapper.updateEntity(request, membresia);
        membresia.setUsuario(usuario);
        Membresia updated = membresiaRepository.save(membresia);
        return membresiaMapper.toResponse(updated);
    }

    public void deleteByUsuarioEmail(String usuarioEmail) {
        String emailNotNull = Objects.requireNonNull(usuarioEmail, "El email del usuario es obligatorio");
        Membresia membresia = membresiaRepository.findByUsuarioEmail(emailNotNull)
                .orElseThrow(() -> new MembresiaNotFoundException(emailNotNull, "usuario"));
        membresiaRepository.delete(membresia);
    }
}