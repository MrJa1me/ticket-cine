package cl.ticketcine.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TicketCinePagosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketCinePagosApplication.class, args);
	}

}
