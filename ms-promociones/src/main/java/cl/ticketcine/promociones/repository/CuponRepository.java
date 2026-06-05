package cl.ticketcine.promociones.repository;

import cl.ticketcine.promociones.model.entity.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, String> {
}
