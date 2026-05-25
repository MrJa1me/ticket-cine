package cl.ticketcine.repository;

import cl.ticketcine.model.Campania;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaniaRepository extends JpaRepository<Campania, Integer> {
}