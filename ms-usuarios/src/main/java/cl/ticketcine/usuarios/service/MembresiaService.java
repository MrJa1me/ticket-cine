package cl.ticketcine.usuarios.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.ticketcine.usuarios.dto.MembresiaRequest;
import cl.ticketcine.usuarios.dto.MembresiaResponse;
import cl.ticketcine.usuarios.exception.MembresiaNotFoundException;
import cl.ticketcine.usuarios.mapper.MembresiaMapper;
import cl.ticketcine.usuarios.model.Membresia;
import cl.ticketcine.usuarios.model.Usuario;
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

    public List<MembresiaResponse> findByUsuarioEmail(String usuarioEmail) {
        List<Membresia> membresias = membresiaRepository.findByUsuarioEmail(usuarioEmail);
        if (membresias.isEmpty()) {
            throw new MembresiaNotFoundException(usuarioEmail, "cualquier nivel");
        }
        return membresiaMapper.toResponseList(membresias);
    }

    public MembresiaResponse create(MembresiaRequest request) {
        String email = Objects.requireNonNull(request.getUsuarioEmail(), "El email del usuario es obligatorio");
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + request.getUsuarioEmail()));

        Membresia membresia = membresiaMapper.toEntity(request);
        membresia.setUsuario(usuario);
        Membresia saved = membresiaRepository.save(membresia);
        return membresiaMapper.toResponse(saved);
    }

    public MembresiaResponse update(Integer id, MembresiaRequest request) {
        int idToUpdate = Objects.requireNonNull(id, "El ID de la membresía es obligatorio");
        Membresia membresia = membresiaRepository.findById(idToUpdate)
                .orElseThrow(() -> new MembresiaNotFoundException(idToUpdate));

        String email = Objects.requireNonNull(request.getUsuarioEmail(), "El email del usuario es obligatorio");
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + request.getUsuarioEmail()));

        membresiaMapper.updateEntity(request, membresia);
        membresia.setUsuario(usuario);
        Membresia updated = membresiaRepository.save(membresia);
        return membresiaMapper.toResponse(updated);
    }

    public void delete(Integer id) {
        Integer idToDelete = Objects.requireNonNull(id, "El ID de la membresía es obligatorio");
        if (!membresiaRepository.existsById(idToDelete)) {
            throw new MembresiaNotFoundException(idToDelete);
        }
        membresiaRepository.deleteById(id);
    }
}