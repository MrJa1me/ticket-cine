package cl.ticketcine.reserva.repository;

import cl.ticketcine.reserva.model.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {

    Optional<Mantenimiento> findByIdMaint(Integer idMaint);

    List<Mantenimiento> findBySalaIdSala(String idSala);

    boolean existsByIdMaint(Integer idMaint);
}
