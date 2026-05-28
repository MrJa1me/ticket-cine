package cl.ticketcine.reserva.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


@Repository
public interface UsuarioProyeccionRepository extends JpaRepository<UsuarioProyeccion, String> {

    Optional<UsuarioProyeccion> findByEmail(String email);
    void deleteByEmail(String email);

}