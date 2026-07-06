package cl.ticketcine.catalogo.repository;

import cl.ticketcine.catalogo.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
    boolean existsByNombrePeliculaIgnoreCaseAndFecha(String nombrePelicula, LocalDate fecha);
    boolean existsByNombrePeliculaIgnoreCaseAndFechaAndIdPeliculaNot(String nombrePelicula, LocalDate fecha, Integer idPelicula);
}
