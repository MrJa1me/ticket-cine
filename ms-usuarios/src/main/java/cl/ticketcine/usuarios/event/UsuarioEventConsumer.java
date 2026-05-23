package cl.ticketcine.usuarios.event;

import cl.ticketcine.common.event.UsuarioCreatedEvent;
import cl.ticketcine.common.event.UsuarioDeletedEvent;
import cl.ticketcine.common.event.UsuarioUpdatedEvent;
import cl.ticketcine.usuarios.model.entity.Usuario;
import cl.ticketcine.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioEventConsumer {

    private final UsuarioRepository usuarioRepository;

    @KafkaListener(topics = "autenticacion.usuario.created", groupId = "usuarios-group")
    @Transactional
    public void handleUsuarioCreated(UsuarioCreatedEvent event) {
        log.info("Evento recibido: Usuario creado - email: {}, nombre: {}", event.getEmail(), event.getNombre());

        if (usuarioRepository.existsByEmail(event.getEmail())) {
            log.warn("El usuario con email {} ya existe en la base de datos de usuarios. Ignorando evento.", event.getEmail());
            return;
        }

        Usuario usuario = Usuario.builder()
                .email(event.getEmail())
                .nombre(event.getNombre() != null ? event.getNombre() : event.getEmail())
                .fechaRegistro(java.time.LocalDate.now())
                .build();

        usuarioRepository.save(usuario);
        log.info("Usuario sincronizado exitosamente en ms-usuarios: {}", event.getEmail());
    }

    @KafkaListener(topics = "autenticacion.usuario.updated", groupId = "usuarios-group")
    @Transactional
    public void handleUsuarioUpdated(UsuarioUpdatedEvent event) {
        log.info("Evento recibido: Usuario actualizado - email: {}, nombre: {}", event.getEmail(), event.getNombre());

        usuarioRepository.findByEmail(event.getEmail()).ifPresentOrElse(
                usuario -> {
                    if (event.getNombre() != null) {
                        usuario.setNombre(event.getNombre());
                    }
                    usuarioRepository.save(usuario);
                    log.info("Usuario actualizado exitosamente en ms-usuarios: {}", event.getEmail());
                },
                () -> log.warn("Usuario con email {} no encontrado en ms-usuarios para actualizar. Ignorando evento.", event.getEmail())
        );
    }

    @KafkaListener(topics = "autenticacion.usuario.deleted", groupId = "usuarios-group")
    @Transactional
    public void handleUsuarioDeleted(UsuarioDeletedEvent event) {
        log.info("Evento recibido: Usuario eliminado - email: {}", event.getEmail());

        usuarioRepository.findByEmail(event.getEmail()).ifPresentOrElse(
                usuario -> {
                    usuarioRepository.delete(usuario);
                    log.info("Usuario eliminado exitosamente en ms-usuarios: {}", event.getEmail());
                },
                () -> log.warn("Usuario con email {} no encontrado en ms-usuarios para eliminar. Ignorando evento.", event.getEmail())
        );
    }
}