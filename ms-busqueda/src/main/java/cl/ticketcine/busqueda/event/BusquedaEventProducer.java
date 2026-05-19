package cl.ticketcine.busqueda.event;

import cl.ticketcine.common.event.PeliculaCreatedEvent;
import cl.ticketcine.common.event.PeliculaDeletedEvent;
import cl.ticketcine.common.event.PeliculaUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BusquedaEventProducer {

    private static final String TOPIC_CREATED = "busqueda.pelicula.created";
    private static final String TOPIC_UPDATED = "busqueda.pelicula.updated";
    private static final String TOPIC_DELETED = "busqueda.pelicula.deleted";

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPeliculaCreated(String slug, String titulo, Integer idCat, Integer duracionMin, Integer estrenoAnio) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_CREATED); return; }
        try {
            PeliculaCreatedEvent event = PeliculaCreatedEvent.builder().slug(slug).titulo(titulo).idCat(idCat).duracionMin(duracionMin).estrenoAnio(estrenoAnio).build();
            log.info("Publicando evento {}: slug={}", TOPIC_CREATED, slug);
            kafkaTemplate.send(TOPIC_CREATED, slug, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }

    public void publishPeliculaUpdated(String slug, String titulo, Integer idCat, Integer duracionMin, Integer estrenoAnio) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_UPDATED); return; }
        try {
            PeliculaUpdatedEvent event = PeliculaUpdatedEvent.builder().slug(slug).titulo(titulo).idCat(idCat).duracionMin(duracionMin).estrenoAnio(estrenoAnio).build();
            log.info("Publicando evento {}: slug={}", TOPIC_UPDATED, slug);
            kafkaTemplate.send(TOPIC_UPDATED, slug, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }

    public void publishPeliculaDeleted(String slug) {
        if (kafkaTemplate == null) { log.warn("Kafka no disponible, evento ignorado: {}", TOPIC_DELETED); return; }
        try {
            PeliculaDeletedEvent event = PeliculaDeletedEvent.builder().slug(slug).build();
            log.info("Publicando evento {}: slug={}", TOPIC_DELETED, slug);
            kafkaTemplate.send(TOPIC_DELETED, slug, event);
        } catch (Exception e) {
            log.warn("Error publicando evento Kafka: {}", e.getMessage());
        }
    }
}
