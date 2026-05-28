package cl.ticketcine.reserva.event;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioEventConsumer {

    private final UsuarioProyeccionService usuarioProyeccionService;

    @KafkaListener(  //es lo mismo que el productor publicó
        topics = "usuarios.usuario.created",
        groupId = "ms-reserva",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.UsuarioCreatedEvent"}
    )
    @Transactional
    public void onUsuarioCreated(UsuarioCreatedEvent event) {
        log.debug("Evento recibido → created, mail: {}", event.getEmail());
        usuarioProyeccionService.save(event.getEmail(), event.getNombre()); //rellenar todos los campos que mandamos del common created
    }


    @KafkaListener(
        topics = "usuarios.usuario.updated",
        groupId = "ms-reserva",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.UsuarioUpdatedEvent"}
    )
    @Transactional
    public void onUsuarioUpdated(UsuarioUpdatedEvent event) {
        log.debug("Evento recibido → updated, email: {}", event.getEmail());
        // Usamos los datos del evento (los nuevos), no los de la BD (los viejos)
        usuarioProyeccionService.save(event.getEmail(), event.getNombre());
    }

    @KafkaListener(
        topics = "usuarios.usuario.deleted",
        groupId = "ms-reserva",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.UsuarioDeletedEvent"}
    )
    @Transactional
    public void onUsuarioDeleted(UsuarioDeletedEvent event) {
        log.debug("Evento recibido → deleted, email: {}", event.getEmail());
        usuarioProyeccionService.deleteByEmail(event.getEmail());
    }
}

//tiene que tomar el dato de kafka y guardarlo en una tabla de proyeccion