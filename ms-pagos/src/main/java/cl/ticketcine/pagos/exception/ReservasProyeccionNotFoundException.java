package cl.ticketcine.pagos.exception;

import java.util.UUID;

public class ReservasProyeccionNotFoundException extends RuntimeException {

	public ReservasProyeccionNotFoundException(UUID id) {
		super("No se encontró ninguna reserva con id: " + id);
	}
}
