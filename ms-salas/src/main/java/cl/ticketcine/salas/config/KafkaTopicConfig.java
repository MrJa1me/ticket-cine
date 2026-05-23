package cl.ticketcine.salas.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuracion de topics Kafka para eventos de salas.
 */
@Slf4j
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicSalaCreated() {
        log.debug("Creando topico Kafka: salas.sala.created");
        return TopicBuilder.name("salas.sala.created")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicSalaUpdated() {
        log.debug("Creando topico Kafka: salas.sala.updated");
        return TopicBuilder.name("salas.sala.updated")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicSalaDeleted() {
        log.debug("Creando topico Kafka: salas.sala.deleted");
        return TopicBuilder.name("salas.sala.deleted")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
