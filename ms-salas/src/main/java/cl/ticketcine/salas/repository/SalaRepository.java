package cl.ticketcine.salas.repository;

import cl.ticketcine.salas.model.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, String> {

    List<Sala> findByFormato(String formato);

    List<Sala> findByCapacidadGreaterThanEqual(Integer capacidad);
}
