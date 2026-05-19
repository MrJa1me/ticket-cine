package cl.ticketcine.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Clase principal del microservicio de Autenticacion (TicketCine).
 *
 * @EnableDiscoveryClient  -> registra este microservicio en Eureka para discovery.
 * @EnableFeignClients     -> habilita clientes Feign para comunicacion sincronica
 *                            con otros microservicios (ms-usuarios, etc.).
 * @SpringBootApplication  -> configuracion automatica de Spring Boot.
 *
 * La autenticacion se maneja de forma simple a traves de la arquitectura
 * de capas tradicional (Controller -> Service -> Repository -> DB),
 * sin Spring Security ni JWT.
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.ticketcine.autenticacion.client")
@SpringBootApplication
public class TicketcineAutenticacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketcineAutenticacionApplication.class, args);
    }

}
