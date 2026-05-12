package cl.triskeledu.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.triskeledu.catalogo.model.Libro;

@Repository
public interface CategoriaRepository extends JpaRepository<Libro, Integer> {
    
}