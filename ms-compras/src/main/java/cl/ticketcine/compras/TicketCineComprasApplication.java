package cl.ticketcine.compras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TicketCineComprasApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketCineComprasApplication.class, args);
	}

}
