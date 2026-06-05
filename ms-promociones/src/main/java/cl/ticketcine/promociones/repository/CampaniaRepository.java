package cl.ticketcine.promociones.repository;

import cl.ticketcine.promociones.model.entity.Campania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaniaRepository extends JpaRepository<Campania, Long> {
}
