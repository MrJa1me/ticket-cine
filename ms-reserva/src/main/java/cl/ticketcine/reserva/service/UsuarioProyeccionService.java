package cl.ticketcine.reserva.service;

import cl.ticketcine.reserva.dto.UsuarioProyeccionResponse;
import cl.ticketcine.reserva.mapper.UsuarioProyeccionMapper;
import cl.ticketcine.reserva.model.UsuarioProyeccion;
import cl.ticketcine.reserva.repository.UsuarioProyeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioProyeccionService {

    private final UsuarioProyeccionRepository usuarioProyeccionRepository;
    private final UsuarioProyeccionMapper usuarioProyeccionMapper;

    @Transactional(readOnly = true)
    public List<UsuarioProyeccionResponse> findAll() {
        return usuarioProyeccionMapper.toResponseList(usuarioProyeccionRepository.findAll());
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
        UsuarioProyeccion usuarioProyeccion = findByEmail(email);
        usuarioProyeccionRepository.delete(usuarioProyeccion);
    }

    @Transactional(readOnly = true)
    public UsuarioProyeccion findByEmail(String email) {
        return usuarioProyeccionRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún usuario con email: " + email));
    }
}
