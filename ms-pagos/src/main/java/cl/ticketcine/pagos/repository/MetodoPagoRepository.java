package cl.ticketcine.pagos.repository;

import cl.ticketcine.pagos.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, String> {

}
