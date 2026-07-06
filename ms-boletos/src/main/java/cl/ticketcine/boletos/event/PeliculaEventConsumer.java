package cl.ticketcine.boletos.event;

import cl.ticketcine.common.event.PeliculaCreatedEvent;
import cl.ticketcine.common.event.PeliculaDeletedEvent;
import cl.ticketcine.common.event.PeliculaUpdatedEvent;
import cl.ticketcine.boletos.service.PeliculaProyeccionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PeliculaEventConsumer {

    private final PeliculaProyeccionService PeliculaProyeccionService;
    
    @KafkaListener(
        topics = "catalogo.Pelicula.created",
        groupId = "ms-boletos",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.PeliculaCreatedEvent"}
    )
    @Transactional
    public void onPeliculaCreated(PeliculaCreatedEvent event) {
        log.debug("Pelicula recibido → created, idPelicula: {}", event.getIdPelicula());
        PeliculaProyeccionService.save(event.getIdPelicula(), event.getNombrePelicula(), event.getFecha());
    }

    @KafkaListener(
        topics = "catalogo.Pelicula.updated",
        groupId = "ms-boletos",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.PeliculaUpdatedEvent"}
    )
    @Transactional
    public void onPeliculaUpdated(PeliculaUpdatedEvent event) {
        log.debug("Pelicula recibido → updated, idPelicula: {}", event.getIdPelicula());
        PeliculaProyeccionService.save(event.getIdPelicula(), event.getNombrePelicula(), event.getFecha());
    }

    @KafkaListener(
        topics = "catalogo.Pelicula.deleted",
        groupId = "ms-boletos",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.PeliculaDeletedEvent"}
    )
    @Transactional
    public void onPeliculaDeleted(PeliculaDeletedEvent event) {
        log.debug("Pelicula recibido → deleted, idPelicula: {}", event.getIdPelicula());
        PeliculaProyeccionService.eliminarProyeccion(event.getIdPelicula());
    }
}
