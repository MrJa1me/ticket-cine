package cl.ticketcine.salas.event;

import cl.ticketcine.common.event.SalaCreatedEvent;
import cl.ticketcine.common.event.SalaDeletedEvent;
import cl.ticketcine.common.event.SalaUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalaEventProducer {

    private static final String TOPIC_CREATED = "salas.sala.created";
    private static final String TOPIC_UPDATED = "salas.sala.updated";
    private static final String TOPIC_DELETED = "salas.sala.deleted";

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSalaCreated(@NonNull String idSala, String formato, Integer capacidad) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_CREATED); return; }
        try {
            SalaCreatedEvent event = SalaCreatedEvent.builder().idSala(idSala).formato(formato).capacidad(capacidad).build();
            log.info("Publicando evento {}: idSala={}", TOPIC_CREATED, idSala);
            kafkaTemplate.send(TOPIC_CREATED, idSala, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }

    public void publishSalaUpdated(@NonNull String idSala, String formato, Integer capacidad) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_UPDATED); return; }
        try {
            SalaUpdatedEvent event = SalaUpdatedEvent.builder().idSala(idSala).formato(formato).capacidad(capacidad).build();
            log.info("Publicando evento {}: idSala={}", TOPIC_UPDATED, idSala);
            kafkaTemplate.send(TOPIC_UPDATED, idSala, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }

    public void publishSalaDeleted(@NonNull String idSala) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_DELETED); return; }
        try {
            SalaDeletedEvent event = SalaDeletedEvent.builder().idSala(idSala).build();
            log.info("Publicando evento {}: idSala={}", TOPIC_DELETED, idSala);
            kafkaTemplate.send(TOPIC_DELETED, idSala, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }
}
