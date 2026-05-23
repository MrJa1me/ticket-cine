package cl.ticketcine.pagos.exception;

public class TransaccionNotFoundException extends RuntimeException {

	public TransaccionNotFoundException(Long id) {
		super("No se encontró ninguna transacción con id: " + id);
	}
}
