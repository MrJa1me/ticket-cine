package cl.ticketcine.reserva.repository;

import cl.ticketcine.reserva.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface reservaRepository extends JpaRepository<Asiento, Integer> {

    Optional<Asiento> findByIdAsiento(Integer idAsiento);

    List<Asiento> findBySalaIdSala(String idSala);

    boolean existsByIdAsiento(Integer idAsiento);
}
