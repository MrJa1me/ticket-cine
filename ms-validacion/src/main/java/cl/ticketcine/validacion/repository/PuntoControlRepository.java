package cl.ticketcine.validacion.repository;

import cl.ticketcine.validacion.model.entity.PuntoControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuntoControlRepository extends JpaRepository<PuntoControl, String> {

    List<PuntoControl> findByUbicacionContainingIgnoreCase(String ubicacion);
}
