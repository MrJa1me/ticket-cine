package cl.ticketcine.notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TicketcineNotificacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketcineNotificacionesApplication.class, args);
	}

}
