package cl.ticketcine.notificaciones.repository;

import cl.ticketcine.notificaciones.model.ColaEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface notificacionesRepository extends JpaRepository<ColaEnvio, Long> {
}
