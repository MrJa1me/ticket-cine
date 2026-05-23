package cl.ticketcine.busqueda.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@Configuration
public class KafkaTopicConfig {

    // Topics que PUBLICA ms-busqueda
    @Bean
    public NewTopic topicPeliculaCreated() {
        log.debug("Creando topico Kafka: busqueda.pelicula.created");
        return TopicBuilder.name("busqueda.pelicula.created")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicPeliculaUpdated() {
        log.debug("Creando topico Kafka: busqueda.pelicula.updated");
        return TopicBuilder.name("busqueda.pelicula.updated")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicPeliculaDeleted() {
        log.debug("Creando topico Kafka: busqueda.pelicula.deleted");
        return TopicBuilder.name("busqueda.pelicula.deleted")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
