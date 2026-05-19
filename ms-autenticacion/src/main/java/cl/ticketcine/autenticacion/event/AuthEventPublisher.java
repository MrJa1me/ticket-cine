package cl.ticketcine.autenticacion.event;

import cl.ticketcine.common.event.UsuarioCreatedEvent;
import cl.ticketcine.common.event.UsuarioDeletedEvent;
import cl.ticketcine.common.event.UsuarioUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthEventPublisher {

    private static final String TOPIC_CREATED = "autenticacion.usuario.created";
    private static final String TOPIC_UPDATED = "autenticacion.usuario.updated";
    private static final String TOPIC_DELETED = "autenticacion.usuario.deleted";

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUsuarioCreated(String email, String nombre) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_CREATED); return; }
        try {
            UsuarioCreatedEvent event = UsuarioCreatedEvent.builder()
                    .email(email).nombre(nombre).build();
            log.info("Publicando evento {}: email={}", TOPIC_CREATED, email);
            kafkaTemplate.send(TOPIC_CREATED, email, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }

    public void publishUsuarioUpdated(String email, String nombre) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_UPDATED); return; }
        try {
            UsuarioUpdatedEvent event = UsuarioUpdatedEvent.builder()
                    .email(email).nombre(nombre).build();
            log.info("Publicando evento {}: email={}", TOPIC_UPDATED, email);
            kafkaTemplate.send(TOPIC_UPDATED, email, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }

    public void publishUsuarioDeleted(String email) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_DELETED); return; }
        try {
            UsuarioDeletedEvent event = UsuarioDeletedEvent.builder()
                    .email(email).build();
            log.info("Publicando evento {}: email={}", TOPIC_DELETED, email);
            kafkaTemplate.send(TOPIC_DELETED, email, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }
}
