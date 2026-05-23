package cl.ticketcine.pagos.repository;

import cl.ticketcine.pagos.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

}
