package cl.ticketcine.busqueda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.ticketcine.busqueda.client")
public class TicketcineBusquedaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketcineBusquedaApplication.class, args);
	}

}
