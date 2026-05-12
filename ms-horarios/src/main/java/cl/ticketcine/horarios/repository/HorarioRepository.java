package cl.ticketcine.horarios.repository;

import cl.ticketcine.horarios.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByFecha(LocalDate fecha);

    List<Horario> findByPeliculaContainingIgnoreCase(String pelicula);

    Optional<Horario> findByPeliculaIgnoreCaseAndFechaAndHora(
            String pelicula,
            LocalDate fecha,
            LocalTime hora);
}
