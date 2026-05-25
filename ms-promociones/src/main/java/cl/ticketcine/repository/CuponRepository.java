package cl.ticketcine.repository;

import cl.ticketcine.model.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuponRepository extends JpaRepository<Cupon, String> {
}