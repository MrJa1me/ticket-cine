package cl.ticketcine.validacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.ticketcine.validacion.client")
@EnableJpaAuditing
public class TicketcineValidacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketcineValidacionApplication.class, args);
	}

}
