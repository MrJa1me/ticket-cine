package cl.ticketcine.busqueda.repository;

import cl.ticketcine.busqueda.model.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula, String> {

    List<Pelicula> findByTituloContainingIgnoreCase(String titulo);

    List<Pelicula> findByCategoria_IdCat(Integer idCat);

    List<Pelicula> findByEstrenoAnio(Integer estrenoAnio);
}
