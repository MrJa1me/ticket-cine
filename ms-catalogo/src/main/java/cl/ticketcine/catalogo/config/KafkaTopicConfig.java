package cl.ticketcine.catalogo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicPeliculaCreated() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "catalogo.Pelicula.created");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("catalogo.Pelicula.created").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topicPeliculaUpdated() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "catalogo.Pelicula.updated");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("catalogo.Pelicula.updated").partitions(1).build();
    }

    @Bean
    public NewTopic topicPeliculaDeleted() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "catalogo.Pelicula.deleted");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("catalogo.Pelicula.deleted").partitions(1).build();
    }
}