package cl.ticketcine.busqueda.event;

import cl.ticketcine.common.event.UsuarioCreatedEvent;
import cl.ticketcine.common.event.UsuarioDeletedEvent;
import cl.ticketcine.common.event.UsuarioUpdatedEvent;
import cl.ticketcine.busqueda.model.entity.UsuarioProyeccion;
import cl.ticketcine.busqueda.repository.UsuarioProyeccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Consumidor de eventos de usuario desde ms-usuarios.
 * Mantiene la proyeccion de usuarios actualizada en ms-busqueda.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioEventConsumer {

    private final UsuarioProyeccionRepository usuarioProyeccionRepository;

    @KafkaListener(topics = "usuarios.usuario.created", groupId = "busqueda-group")
    @Transactional
    public void handleUsuarioCreated(UsuarioCreatedEvent event) {
        log.info("Evento recibido en ms-busqueda: Usuario creado - email: {}", event.getEmail());

        if (usuarioProyeccionRepository.existsById(event.getEmail())) {
            log.warn("El usuario con email {} ya existe en la proyeccion de busqueda. Ignorando.", event.getEmail());
            return;
        }

        UsuarioProyeccion usuario = UsuarioProyeccion.builder()
                .email(event.getEmail())
                .esEstudiante(false)
                .build();

        usuarioProyeccionRepository.save(usuario);
        log.info("Usuario sincronizado en ms-busqueda: {}", event.getEmail());
    }

    @KafkaListener(topics = "usuarios.usuario.updated", groupId = "busqueda-group")
    @Transactional
    public void handleUsuarioUpdated(UsuarioUpdatedEvent event) {
        log.info("Evento recibido en ms-busqueda: Usuario actualizado - email: {}", event.getEmail());

        usuarioProyeccionRepository.findById(event.getEmail()).ifPresentOrElse(
                usuario -> {
                    usuarioProyeccionRepository.save(usuario);
                    log.info("Usuario actualizado en ms-busqueda: {}", event.getEmail());
                },
                () -> {
                    log.warn("Usuario con email {} no encontrado en proyeccion de busqueda. Creando...", event.getEmail());
                    UsuarioProyeccion nuevo = UsuarioProyeccion.builder()
                            .email(event.getEmail())
                            .esEstudiante(false)
                            .build();
                    usuarioProyeccionRepository.save(nuevo);
                }
        );
    }

    @KafkaListener(topics = "usuarios.usuario.deleted", groupId = "busqueda-group")
    @Transactional
    public void handleUsuarioDeleted(UsuarioDeletedEvent event) {
        log.info("Evento recibido en ms-busqueda: Usuario eliminado - email: {}", event.getEmail());

        usuarioProyeccionRepository.findById(event.getEmail()).ifPresentOrElse(
                usuario -> {
                    usuarioProyeccionRepository.delete(usuario);
                    log.info("Usuario eliminado de proyeccion en ms-busqueda: {}", event.getEmail());
                },
                () -> log.warn("Usuario con email {} no encontrado en proyeccion de busqueda. Ignorando.", event.getEmail())
        );
    }
}
