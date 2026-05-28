package cl.ticketcine.reserva.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de aplicar las reglas de negocio de recursos físicos:
 * - Gestiona operaciones CRUD sobre recursos físicos del inventario.
 * - Valida que el SKU sea único (clave de negocio del dominio de recursos).
 * - Valida que si el tipo de recurso es 'Libro', el ISBN debe existir en la proyección local.
 * - Permite filtrar recursos por disponibilidad y tipo.
 * - Lanza excepciones personalizadas del módulo common para casos de error específicos.
 * - Utiliza un mapper para convertir entre entidades y DTOs, manteniendo el código limpio y separado.
 */

//
@Service
@RequiredArgsConstructor
public class UsuarioProyeccionService {

    private final UsuarioProyeccionRepository usuarioProyeccionRepository;
    private final RecursoFisicoRepository recursoFisicoRepository;
    private final CatalogoClient catalogoClient;
    private final UsuarioProyeccionMapper usuarioProyeccionMapper;

    @Transactional
    public List<UsuarioProyeccionResponse> findAll() {
        return usuarioProyeccionMapper.toResponseList(usuarioroyeccionRepository.findAll());
    }

    @Transactional
    public void save(String email, String nombre) {
        UsuarioProyeccion usuarioProyeccion = new UsuarioProyeccion();
        usuarioProyeccion.setEmail(email);
        usuarioProyeccion.setNombre(nombre);
        usuarioProyeccionRepository.save(usuarioProyeccion);
    }

    @Transactional
    public void deleteByEmail(String email) {
        UsuarioProyeccion usuarioProyeccion = findByemail(email);
        List<String> tablasAsociadas = new ArrayList<>();
        if (!usuarioProyeccionRepository.existsByUsuarioEmail(usuarioProyeccion.getEmail())) tablasAsociadas.add("Usuario");
        if (!tablasAsociadas.isEmpty()) throw new ReferentialIntegrityException("Usuario Proyección", email, String.join(", ", tablasAsociadas));
        usuarioProyeccionRepository.delete(usuarioProyeccion);
    }

    public UsuarioProyeccion findByEmail(String email) {
        return usuarioProyeccionRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario Proyeccion", "EMAIL", email));
    }

}
