package cl.ticketcine.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TicketcineUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketcineUsuariosApplication.class, args);
	}

}
