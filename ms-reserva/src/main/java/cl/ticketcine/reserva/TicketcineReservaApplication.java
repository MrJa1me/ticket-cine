package cl.ticketcine.reserva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cl.ticketcine.reserva.client")
public class TicketcineReservaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketcineReservaApplication.class, args);
	}

}
