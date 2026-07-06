package cl.ticketcine.pagos.repository;

import cl.ticketcine.pagos.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer>{

}
