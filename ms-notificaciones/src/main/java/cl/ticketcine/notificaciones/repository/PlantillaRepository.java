package cl.ticketcine.notificaciones.repository;

import cl.ticketcine.notificaciones.model.Plantilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantillaRepository extends JpaRepository<Plantilla, String> {
}
