package cl.ticketcine.promociones.repository;

import cl.ticketcine.promociones.model.entity.UsuarioProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioProyeccionRepository extends JpaRepository<UsuarioProyeccion, String> {
}
