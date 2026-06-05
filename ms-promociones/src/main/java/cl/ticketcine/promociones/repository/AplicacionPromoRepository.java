package cl.ticketcine.promociones.repository;

import cl.ticketcine.promociones.model.entity.AplicacionPromo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicacionPromoRepository extends JpaRepository<AplicacionPromo, Long> {
}
