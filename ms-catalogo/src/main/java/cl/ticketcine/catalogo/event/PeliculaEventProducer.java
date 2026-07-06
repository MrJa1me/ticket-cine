package cl.ticketcine.catalogo.event;

import cl.ticketcine.common.event.PeliculaCreatedEvent;
import cl.ticketcine.common.event.PeliculaDeletedEvent;
import cl.ticketcine.common.event.PeliculaEvent;
import cl.ticketcine.common.event.PeliculaUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PeliculaEventProducer {

    private static final String TOPIC_BASE = "catalogo.Pelicula";
    private static final String ID_NOT_NULL = "El ID del Pelicula no puede ser null";
    private static final String TOPIC_NOT_NULL = "El topic no puede ser null";

    private final KafkaTemplate<String, PeliculaEvent> kafkaTemplate;

    private void send(PeliculaEvent event, String eventType) {
        String topic = Objects.requireNonNull(String.format("%s.%s", TOPIC_BASE, eventType), TOPIC_NOT_NULL);
        String idPelicula = Objects.requireNonNull(event.getIdPelicula().toString(), ID_NOT_NULL);

        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("");
        log.debug("Enviando Pelicula Kafka → topic: {}, key: {}", topic, idPelicula);
        log.debug("");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        
        kafkaTemplate.send(topic, idPelicula, event);
    }

    public void sendCreated(PeliculaCreatedEvent event) {
        send(event, "created");
    }

    public void sendUpdated(PeliculaUpdatedEvent event) {
        send(event, "updated");
    }

    public void sendDeleted(PeliculaDeletedEvent event) {
        send(event, "deleted");
    }
}
