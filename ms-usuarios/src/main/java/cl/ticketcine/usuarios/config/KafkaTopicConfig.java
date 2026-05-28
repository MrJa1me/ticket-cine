package cl.ticketcine.usuarios.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@Configuration
public class KafkaTopicConfig {

    // Topics que CONSUME (vienen de ms-autenticacion)
    @Bean
    public NewTopic topicAuthUsuarioCreated() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "autenticacion.usuario.created");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("usuarios.usuario.created")
                .partitions(1) // En desarrollo con 1 está bien
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicAuthUsuarioUpdated() {
        log.debug("Creando topico Kafka: autenticacion.usuario.updated");
        return TopicBuilder.name("usuarios.autenticacion.updated")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicAuthUsuarioDeleted() {
        log.debug("Creando topico Kafka: autenticacion.usuario.deleted");
        return TopicBuilder.name("usuarios.autenticacion.deleted")
                .partitions(1)
                .replicas(1)
                .build();
    }

    // Topics que PUBLICA (para que ms-busqueda y ms-validacion consuman)
    @Bean
    public NewTopic topicUsuarioCreated() {
        log.debug("Creando topico Kafka: usuarios.usuario.created");
        return TopicBuilder.name("usuarios.usuario.created")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicUsuarioUpdated() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "usuarios.usuario.updated");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("usuarios.usuario.updated")
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic topicUsuarioDeleted() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "catalogo.libro.deleted");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("usuarios.usuario.deleted")
                .partitions(1)
                .replicas(1)
                .build();
    }
}