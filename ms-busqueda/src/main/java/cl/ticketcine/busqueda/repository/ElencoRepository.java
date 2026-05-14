package cl.ticketcine.busqueda.repository;

import cl.ticketcine.busqueda.model.entity.Elenco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElencoRepository extends JpaRepository<Elenco, Integer> {
}
