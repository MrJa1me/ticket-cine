package cl.ticketcine.salas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.ticketcine.salas.client")
public class TicketcineSalasApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketcineSalasApplication.class, args);
	}

}
