package cl.ticketcine.reserva.repository;

import cl.ticketcine.reserva.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface salaRepository extends JpaRepository<Sala, String> {

    Optional<Sala> findByIdSala(String idSala);

    boolean existsByIdSala(String idSala);
}
