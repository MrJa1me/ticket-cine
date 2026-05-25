package cl.ticketcine.pagos.repository;

import cl.ticketcine.pagos.model.ReservasProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservasProyeccionRepository extends JpaRepository<ReservasProyeccion, UUID> {

}
