package cl.ticketcine.usuarios.event;

import cl.ticketcine.common.event.UsuarioCreatedEvent;
import cl.ticketcine.common.event.UsuarioDeletedEvent;
import cl.ticketcine.common.event.UsuarioEvent;
import cl.ticketcine.common.event.UsuarioUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
  //publica los datos que vienen del maestro, manda los datos a kafka
@Slf4j
@Component
public class UsuarioEventProducer {

    private static final String TOPIC_BASE = "usuarios.usuario";
    private static final String MAIL_NOT_NULL = "El correo electrónico no puede ser null";
    private static final String TOPIC_NOT_NULL = "El topic no puede ser null";

    private final KafkaTemplate<String, UsuarioEvent> kafkaTemplate;

    public UsuarioEventProducer(KafkaTemplate<String, UsuarioEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private void send(UsuarioEvent event, String eventType) {
        String topic = Objects.requireNonNull(String.format("%s.%s", TOPIC_BASE, eventType), TOPIC_NOT_NULL);
        String email = Objects.requireNonNull(event.getEmail(), MAIL_NOT_NULL);

        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("");
        log.debug("Enviando evento Kafka → topic: {}, key: {}", topic, email);
        log.debug("");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");

        kafkaTemplate.send(topic, email, event);
    }
      //3 topicos en kafka
    public void sendCreated(UsuarioCreatedEvent event) {
        send(event, "created");
    }

    public void sendUpdated(UsuarioUpdatedEvent event) {
        send(event, "updated");
    }

    public void sendDeleted(UsuarioDeletedEvent event) {
        send(event, "deleted");
    }
}

