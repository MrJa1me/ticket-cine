package cl.ticketcine.usuarios.repository;

import cl.ticketcine.usuarios.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}