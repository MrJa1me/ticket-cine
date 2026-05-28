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

    @KafkaListener(
        topics = "autenticacion.usuario.created",
        groupId = "ms-usuarios",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.UsuarioCreatedEvent"}
    )
    @Transactional
    public void onUsuarioCreated(UsuarioCreatedEvent event) {
        log.debug("Evento recibido → created, email: {}", event.getEmail());
        Usuario usuario = new Usuario();
        usuario.setEmail(event.getEmail());
        usuario.setNombre(event.getNombre());
        usuarioRepository.save(usuario);
    }

    @KafkaListener(
        topics = "autenticacion.usuario.updated",
        groupId = "ms-usuarios",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.UsuarioUpdatedEvent"}
    )
    @Transactional
    public void onUsuarioUpdated(UsuarioUpdatedEvent event) {
        log.debug("Evento recibido → updated, email: {}", event.getEmail());
        usuarioRepository.findByEmail(event.getEmail()).ifPresent(usuario -> {
            usuario.setNombre(event.getNombre());
            usuarioRepository.save(usuario);
        });
    }

    @KafkaListener(
        topics = "autenticacion.usuario.deleted",
        groupId = "ms-usuarios",
        properties = {"spring.json.value.default.type=cl.ticketcine.common.event.UsuarioDeletedEvent"}
    )
    @Transactional
    public void onUsuarioDeleted(UsuarioDeletedEvent event) {
        log.debug("Evento recibido → deleted, email: {}", event.getEmail());
        usuarioRepository.deleteById(event.getEmail());
    }
}
