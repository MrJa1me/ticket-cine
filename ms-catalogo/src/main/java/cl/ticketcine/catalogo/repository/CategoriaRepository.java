package cl.ticketcine.catalogo.repository;

import cl.ticketcine.catalogo.model.Categoria; // Asegúrate de que este modelo exista
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}