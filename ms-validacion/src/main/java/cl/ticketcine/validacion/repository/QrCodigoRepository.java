package cl.ticketcine.validacion.repository;

import cl.ticketcine.validacion.model.entity.QrCodigo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QrCodigoRepository extends JpaRepository<QrCodigo, Long> {

    List<QrCodigo> findByReservaId(UUID reservaId);

    List<QrCodigo> findByActivo(Boolean activo);
}
