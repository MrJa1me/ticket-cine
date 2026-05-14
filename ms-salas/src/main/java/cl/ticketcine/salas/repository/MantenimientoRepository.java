package cl.ticketcine.salas.repository;

import cl.ticketcine.salas.model.entity.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {

    boolean existsBySala_IdSala(String idSala);
}
