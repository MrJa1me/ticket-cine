package cl.ticketcine.notificaciones.repository;

import cl.ticketcine.notificaciones.model.UsuariosProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosProyeccionRepository extends JpaRepository<UsuariosProyeccion, String> {
}
