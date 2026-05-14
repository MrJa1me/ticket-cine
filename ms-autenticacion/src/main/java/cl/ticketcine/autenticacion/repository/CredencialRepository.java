package cl.ticketcine.autenticacion.repository;

import cl.ticketcine.autenticacion.model.entity.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredencialRepository extends JpaRepository<Credencial, String> {

    boolean existsByUserEmail(String userEmail);
}
