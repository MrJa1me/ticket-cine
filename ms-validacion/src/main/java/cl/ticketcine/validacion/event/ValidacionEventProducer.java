package cl.ticketcine.validacion.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ValidacionEventProducer {

    private static final String TOPIC_QR_CREATED = "validacion.qr.created";
    private static final String TOPIC_ACCESO = "validacion.acceso.registrado";

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishQrCreated(Long idQr, String reservaId, String hashCode) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_QR_CREATED); return; }
        if (reservaId == null) { log.warn("reservaId es null, evento ignorado: {}", TOPIC_QR_CREATED); return; }
        if (hashCode == null) { log.warn("hashCode es null, evento ignorado: {}", TOPIC_QR_CREATED); return; }
        try {
            Map<String, Object> event = Map.of("idQr", idQr, "reservaId", reservaId, "hashCode", hashCode);
            log.info("Publicando evento {}: idQr={}", TOPIC_QR_CREATED, idQr);
            kafkaTemplate.send(TOPIC_QR_CREATED, reservaId, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }

    public void publishAccesoRegistrado(String idPunto, Long idQr) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_ACCESO); return; }
        if (idPunto == null) { log.warn("idPunto es null, evento ignorado: {}", TOPIC_ACCESO); return; }
        try {
            Map<String, Object> event = Map.of("idPunto", idPunto, "idQr", idQr);
            log.info("Publicando evento {}: idPunto={}", TOPIC_ACCESO, idPunto);
            kafkaTemplate.send(TOPIC_ACCESO, idPunto, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }
}
