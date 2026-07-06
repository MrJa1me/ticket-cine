package cl.ticketcine.boletos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TicketCineBoletosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketCineBoletosApplication.class, args);
	}

}
