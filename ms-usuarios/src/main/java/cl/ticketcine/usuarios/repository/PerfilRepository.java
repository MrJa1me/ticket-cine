package cl.ticketcine.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ticketcine.usuarios.model.Perfil;
import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    
    Optional<Perfil> findByUsuarioEmail(String usuarioEmail);

    boolean existsByUsuarioEmail(String usuarioEmail);
}