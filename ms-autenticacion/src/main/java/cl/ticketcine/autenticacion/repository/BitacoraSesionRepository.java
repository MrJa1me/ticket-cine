package cl.ticketcine.autenticacion.repository;

import cl.ticketcine.autenticacion.model.entity.BitacoraSesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitacoraSesionRepository extends JpaRepository<BitacoraSesion, Integer> {
}
