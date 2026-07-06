package cl.ticketcine.compras.repository;

import cl.ticketcine.compras.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Integer>{

}
