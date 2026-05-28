package cl.ticketcine.reserva.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import cl.ticketcine.reserva.model.UsuarioProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UsuarioProyeccionRepository extends JpaRepository<UsuarioProyeccion, String> {

    Optional<UsuarioProyeccion> findByEmail(String email);
    void deleteByEmail(String email);

}