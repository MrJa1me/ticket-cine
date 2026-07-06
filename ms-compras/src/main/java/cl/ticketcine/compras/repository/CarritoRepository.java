package cl.ticketcine.compras.repository;

import cl.ticketcine.compras.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

}
