package cl.ticketcine.boletos.repository;

import cl.ticketcine.boletos.model.ProyPelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyPeliculaRepository extends JpaRepository<ProyPelicula, Integer> {
}