package cl.ticketcine.salas.repository;

import cl.ticketcine.salas.model.entity.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsientoRepository extends JpaRepository<Asiento, Integer> {

    boolean existsBySala_IdSala(String idSala);
}
