package cl.ticketcine.usuarios.repository;

import cl.ticketcine.usuarios.model.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    Optional<Perfil> findByUsuarioEmail(String usuarioEmail);

    boolean existsByUsuarioEmail(String usuarioEmail);
}