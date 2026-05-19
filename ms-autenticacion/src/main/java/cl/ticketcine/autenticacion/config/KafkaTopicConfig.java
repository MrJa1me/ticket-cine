package cl.ticketcine.autenticacion.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuracion de topics Kafka para eventos de autenticacion.
 *
 * Permite comunicacion asincronica entre microservicios:
 * - ms-autenticacion publica eventos cuando se registran/actualizan/eliminan credenciales
 * - Otros microservicios (ms-usuarios, ms-notificaciones) consumen estos eventos
 */
@Slf4j
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicAuthUsuarioCreated() {
        log.debug("Creando topico Kafka: autenticacion.usuario.created");
        return TopicBuilder.name("autenticacion.usuario.created")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicAuthUsuarioUpdated() {
        log.debug("Creando topico Kafka: autenticacion.usuario.updated");
        return TopicBuilder.name("autenticacion.usuario.updated")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicAuthUsuarioDeleted() {
        log.debug("Creando topico Kafka: autenticacion.usuario.deleted");
        return TopicBuilder.name("autenticacion.usuario.deleted")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
