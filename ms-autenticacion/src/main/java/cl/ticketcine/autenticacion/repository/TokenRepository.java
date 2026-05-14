package cl.ticketcine.autenticacion.repository;

import cl.ticketcine.autenticacion.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByJwtSecret(String jwtSecret);
}
