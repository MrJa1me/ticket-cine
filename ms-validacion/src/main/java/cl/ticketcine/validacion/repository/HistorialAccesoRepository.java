package cl.ticketcine.validacion.repository;

import cl.ticketcine.validacion.model.entity.HistorialAcceso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialAccesoRepository extends JpaRepository<HistorialAcceso, Long> {

    List<HistorialAcceso> findByPuntoControl_IdPunto(String idPunto);

    List<HistorialAcceso> findByQrCodigo_IdQr(Long idQr);
}
