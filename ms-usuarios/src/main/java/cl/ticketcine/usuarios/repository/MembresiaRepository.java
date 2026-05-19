package cl.ticketcine.usuarios.repository;

import cl.ticketcine.usuarios.model.entity.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Integer> {

    Optional<Membresia> findByUsuarioEmail(String usuarioEmail);

    boolean existsByUsuarioEmail(String usuarioEmail);
}