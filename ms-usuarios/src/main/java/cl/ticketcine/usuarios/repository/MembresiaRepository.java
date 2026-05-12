package cl.ticketcine.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ticketcine.usuarios.model.Membresia;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Integer> {
    
    List<Membresia> findByUsuarioEmail(String usuarioEmail);

    Optional<Membresia> findByUsuarioEmailAndNivel(String usuarioEmail, String nivel);

    boolean existsByUsuarioEmail(String usuarioEmail);
}