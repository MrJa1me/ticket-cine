package cl.ticketcine.validacion.event;

import cl.ticketcine.common.event.UsuarioCreatedEvent;
import cl.ticketcine.common.event.UsuarioDeletedEvent;
import cl.ticketcine.common.event.UsuarioUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Consumidor de eventos de usuario desde ms-usuarios.
 * Mantiene la proyeccion de reservas actualizada en ms-validacion.
 */
@Component
@Slf4j
public class UsuarioEventConsumer {

    @KafkaListener(
        topics = "usuarios.usuario.created",
        groupId = "validacion-group"
    )
    @Transactional
    public void handleUsuarioCreated(UsuarioCreatedEvent event) {
        log.info("Evento recibido en ms-validacion: Usuario creado - email: {}", event.getEmail());
        // La proyeccion de reservas se actualiza cuando el usuario crea reservas,
        // no necesariamente cuando se crea el usuario. Este consumer esta preparado
        // para futuras funcionalidades.
    }

    @KafkaListener(
        topics = "usuarios.usuario.updated",
        groupId = "validacion-group"
    )
    @Transactional
    public void handleUsuarioUpdated(UsuarioUpdatedEvent event) {
        log.info("Evento recibido en ms-validacion: Usuario actualizado - email: {}", event.getEmail());
        // Actualizar proyecciones si es necesario
    }

    @KafkaListener(
        topics = "usuarios.usuario.deleted",
        groupId = "validacion-group"
    )
    @Transactional
    public void handleUsuarioDeleted(UsuarioDeletedEvent event) {
        log.info("Evento recibido en ms-validacion: Usuario eliminado - email: {}", event.getEmail());
        // Limpiar proyecciones asociadas si es necesario
    }
}
