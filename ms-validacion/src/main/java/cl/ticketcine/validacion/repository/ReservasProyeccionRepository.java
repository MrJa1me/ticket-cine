package cl.ticketcine.validacion.repository;

import cl.ticketcine.validacion.model.entity.ReservasProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservasProyeccionRepository extends JpaRepository<ReservasProyeccion, UUID> {

    List<ReservasProyeccion> findByUserEmail(String userEmail);
}
