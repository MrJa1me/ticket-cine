package cl.ticketcine.notificaciones.repository;

import cl.ticketcine.notificaciones.model.LogError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogErrorRepository extends JpaRepository<LogError, Long> {
}
