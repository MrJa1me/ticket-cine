package cl.ticketcine.validacion.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicValidacionCreated() {
        log.debug("Creando topico Kafka: validacion.qr.created");
        return TopicBuilder.name("validacion.qr.created")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicAccesoRegistrado() {
        log.debug("Creando topico Kafka: validacion.acceso.registrado");
        return TopicBuilder.name("validacion.acceso.registrado")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
