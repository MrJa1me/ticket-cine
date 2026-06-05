package cl.ticketcine.promociones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.ticketcine.promociones.client")
@SpringBootApplication
public class TicketcinePromocionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketcinePromocionesApplication.class, args);
	}

}
